class SLRParser:
    def __init__(self, grammar):
        self.g = grammar
        self.first = {}
        self.follow = {}
        self.canonical_collection = []
        self.action_table = {}
        self.goto_table = {}
        self.augmented_start = "S'"
        self.has_conflicts = False

        
        # augmentam gramatica
        self.g.productions[self.augmented_start] = [[self.g.start_symbol]]
        self.g.non_terminals.add(self.augmented_start)

    def compute_first(self):
        # first terminal este el insusi
        for nt in self.g.non_terminals:
            self.first[nt] = set()
        for t in self.g.terminals:
            self.first[t] = {t}
        
        # se repeta pana cand nu mai apar modif
        changed = True
        while changed:
            changed = False
            for lhs in self.g.non_terminals:
                if lhs == self.augmented_start: continue 
                for rhs in self.g.productions[lhs]:
                    rhs_first = set()
                    all_epsilon = True
                    for symbol in rhs:
                        if symbol == 'epsilon': continue
                        symbol_first = self.first.get(symbol, set())
                        rhs_first.update(symbol_first - {'epsilon'})
                        if 'epsilon' not in symbol_first:
                            all_epsilon = False
                            break
                    if all_epsilon or (len(rhs) == 1 and rhs[0] == 'epsilon'):
                        rhs_first.add('epsilon')
                    if not rhs_first.issubset(self.first[lhs]):
                        self.first[lhs].update(rhs_first)
                        changed = True

    def compute_follow(self):
        for nt in self.g.non_terminals:
            self.follow[nt] = set()
        
        # prima oara se adauga pt simbolul de start semnul dolar
        self.follow[self.g.start_symbol].add('$')
        
        changed = True
        while changed:
            changed = False
            for lhs in self.g.non_terminals:
                if lhs == self.augmented_start: continue
                for rhs in self.g.productions[lhs]:
                    for i, symbol in enumerate(rhs):
                        if symbol in self.g.non_terminals:
                            if i + 1 < len(rhs):
                                rest_first = set()
                                all_eps = True
                                for k in range(i+1, len(rhs)):
                                    s = rhs[k]
                                    f_s = self.first.get(s, {s}) if s in self.g.non_terminals else {s}
                                    rest_first.update(f_s - {'epsilon'})
                                    if 'epsilon' not in f_s:
                                        all_eps = False
                                        break
                                if all_eps: rest_first.add('epsilon')

                                if not (rest_first - {'epsilon'}).issubset(self.follow[symbol]):
                                    self.follow[symbol].update(rest_first - {'epsilon'})
                                    changed = True
                                if 'epsilon' in rest_first:
                                    if not self.follow[lhs].issubset(self.follow[symbol]):
                                        self.follow[symbol].update(self.follow[lhs])
                                        changed = True
                            else:
                                if not self.follow[lhs].issubset(self.follow[symbol]):
                                    self.follow[symbol].update(self.follow[lhs])
                                    changed = True

    # inchiderea LR(0)
    def closure(self, items):
        current_items = items.copy()
        while True:
            new_items = set()
            for (lhs, rhs, dot) in current_items:
                # dc punctul e inaintea unui nt trebuie sa adaugam toate prod nt
                if dot < len(rhs):
                    symbol = rhs[dot]
                    if symbol in self.g.non_terminals:
                        for prod in self.g.productions[symbol]:
                            item = (symbol, tuple(prod), 0)
                            if item not in current_items:
                                new_items.add(item)
            if not new_items: break
            current_items.update(new_items)
        return current_items

    # muta dot peste simbol si apoi face closure
    def goto(self, items, symbol):
        goto_items = set()
        for (lhs, rhs, dot) in items:
            if dot < len(rhs) and rhs[dot] == symbol:
                goto_items.add((lhs, rhs, dot + 1))
        return self.closure(goto_items)

    # construim colectia canonica
    def generate_canonical(self):
        # pt fiecare simbol de dupa dot aplicam goto si daca starea e noua o add
        initial_item = (self.augmented_start, tuple(self.g.productions[self.augmented_start][0]), 0)
        start_state = self.closure({initial_item})
        self.canonical_collection = [start_state]
        while True:
            new_states_added = False
            for i, state in enumerate(self.canonical_collection):
                symbols_after_dot = set()
                for (lhs, rhs, dot) in state:
                    if dot < len(rhs): symbols_after_dot.add(rhs[dot])
                for sym in symbols_after_dot:
                    next_state = self.goto(state, sym)
                    if not next_state: continue
                    if next_state not in self.canonical_collection:
                        self.canonical_collection.append(next_state)
                        new_states_added = True
            if not new_states_added: break

    # construim tabelul cu starile shift reduce sau accept
    def build_table(self):
        self.compute_first()
        self.compute_follow()
        self.generate_canonical()
        
        for i, state in enumerate(self.canonical_collection):
            for (lhs, rhs, dot) in state:
                if dot < len(rhs):
                    # SHIFT
                    a = rhs[dot]
                    if a in self.g.terminals:
                        j_state = self.goto(state, a)
                        if j_state in self.canonical_collection:
                            j = self.canonical_collection.index(j_state)
                            self.add_action(i, a, ('shift', j))
                else:
                    # REDUCE sau ACCEPT
                    if lhs == self.augmented_start:
                        self.add_action(i, '$', ('accept',))
                    else:
                        for a in self.follow[lhs]:
                            self.add_action(i, a, ('reduce', lhs, rhs)) 
            
            for nt in self.g.non_terminals:
                if nt == self.augmented_start: continue
                next_s = self.goto(state, nt)
                if next_s in self.canonical_collection:
                    self.goto_table[(i, nt)] = self.canonical_collection.index(next_s)
         
    # detecteaza conflicte           
    def add_action(self, state_idx, symbol, action):
        key = (state_idx, symbol)
        if key in self.action_table:
            existing = self.action_table[key]
            if existing != action:
                print(f" CONFLICT la starea {state_idx}, simbol '{symbol}': {existing} vs {action}")
                self.has_conflicts = True
        self.action_table[key] = action


    def parse(self, input_tokens):
        tokens = input_tokens + ['$']
        stack = [0]
        cursor = 0
        output_productions = []
        
        print(f" Analizez input: {tokens}")
        
        while True:
            state = stack[-1]
            token = tokens[cursor]
            
            action = self.action_table.get((state, token))
            
            if not action:
                print(f"[EROARE] Eroare de sintaxa la token-ul: '{token}' (Starea {state})")
                return None
            
            if action[0] == 'shift':
                stack.append(token)
                stack.append(action[1])
                cursor += 1
                
            elif action[0] == 'reduce':
                lhs, rhs = action[1], action[2]
                
                if len(rhs) == 1 and rhs[0] == 'epsilon':
                    pop_count = 0
                else:
                    pop_count = len(rhs)
                
                for _ in range(pop_count * 2):
                    stack.pop()
                
                top_state = stack[-1]
                goto_state = self.goto_table.get((top_state, lhs))
                
                if goto_state is None:
                    print(" [EROARE] GOTO invalid.")
                    return None
                
                stack.append(lhs)
                stack.append(goto_state)
                
                rhs_str = " ".join(rhs)
                prod_string = f"{lhs} -> {rhs_str}"
                output_productions.append(prod_string)
                
            elif action[0] == 'accept':
                return output_productions