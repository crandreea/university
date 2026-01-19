def genereaza_AF_ID(nume_fisier="identificatori.txt"):
    litere = [chr(c) for c in range(ord('a'), ord('z')+1)] + \
              [chr(c) for c in range(ord('A'), ord('Z')+1)] + ['_']
    cifre = [str(i) for i in range(10)]
    alfabet = litere + cifre

    with open(nume_fisier, "w", encoding="utf-8") as f:
        f.write("q0 q1\n")
        f.write(" ".join(alfabet) + "\n")
        f.write("q0\n")
        f.write("q1\n")

        for c in litere:
            f.write(f"q0 {c} q1\n")

        for c in alfabet:
            f.write(f"q1 {c} q1\n")

    print(f"Automat ID scris în {nume_fisier}")


def genereaza_AF_REAL(nume_fisier="constanteReale.txt"):
    cifre = [str(i) for i in range(10)]
    alfabet = cifre + ['.']

    with open(nume_fisier, "w", encoding="utf-8") as f:
        f.write("q0 q1 q2 q3\n")
        f.write(" ".join(alfabet) + "\n")
        f.write("q0\n")
        f.write("q3\n")

        for c in cifre:
            f.write(f"q0 {c} q1\n")

        for c in cifre:
            f.write(f"q1 {c} q1\n")

        f.write("q1 . q2\n")

        for c in cifre:
            f.write(f"q2 {c} q3\n")

        for c in cifre:
            f.write(f"q3 {c} q3\n")

    print(f"Automat REAL scris în {nume_fisier}")


if __name__ == "__main__":
    genereaza_AF_ID()
    genereaza_AF_REAL()
