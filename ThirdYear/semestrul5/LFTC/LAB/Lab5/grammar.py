class Grammar:
    def __init__(self, raw_grammar):
        self.productions = {}
        self.start_symbol = None
        self.terminals = set()
        self.non_terminals = set()
        self.parse_grammar(raw_grammar)

    def parse_grammar(self, raw_grammar):
        for line in raw_grammar.strip().split('\n'):
            line = line.strip()
            if not line: continue
            sep = '::=' if '::=' in line else '->'
            if sep not in line: continue
            
            lhs, rhs = line.split(sep)
            lhs = lhs.strip()
            if not self.start_symbol:
                self.start_symbol = lhs
            self.non_terminals.add(lhs)
            
            alternatives = [alt.strip() for alt in rhs.split('|')]
            if lhs not in self.productions:
                self.productions[lhs] = []
            
            for alt in alternatives:
                symbols = alt.split()
                self.productions[lhs].append(symbols)
                for sym in symbols:
                    if sym != 'epsilon':
                        self.terminals.add(sym)
        
        self.terminals = self.terminals - self.non_terminals
        
    def is_non_terminal(self, symbol):
        return symbol in self.non_terminals
