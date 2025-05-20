#include "Matrice.h"
#include "Iterator.h"
#include <exception>

using namespace std;

Matrice::Matrice(int m, int n) {
	/* de adaugat */
    root = nullptr;
    rand = m;
    col = n;
    if(rand <=0 || col <= 0)
        throw std::exception();
}

void clear(Node* node) {
    if (node != nullptr) {
        clear(node->left);
        clear(node->right);
        delete node;
    }
}

//theta(1)
int Matrice::nrLinii() const{
	/* de adaugat */
	return rand;
}

//theta(1)
int Matrice::nrColoane() const{
	/* de adaugat */
	return col;
}
//BC: theta(1)
//WC: thata(log(n))
Node* Matrice::search(Node* node, int linie, int coloana) const{
    if (node == nullptr || (node->data.linie == linie && node->data.coloana == coloana)) {
        return node;
    } else if (linie < node->data.linie || (linie == node->data.linie && coloana < node->data.coloana)) {
        return search(node->left, linie, coloana);
    } else {
        return search(node->right, linie, coloana);
    }
}

//BC: theta(1)
//WC: thata(log(n))
TElem Matrice::element(int i, int j) const{
	/* de adaugat */
    if(i>=rand || j>=col)
        throw exception();
    Node* nd = search(root, i, j);
    TElem valoare;
    if(nd == nullptr)
        valoare = NULL_TELEMENT;
    else
        valoare = nd->data.val;
    return valoare;
}

//BC: theta(1)
//WC: thata(log(n))
void insert(Node*& node, Triplet t) {
    if (node == nullptr) {
        node = new Node(t);
    } else if (t.linie < node->data.linie || (t.linie == node->data.linie && t.coloana < node->data.coloana)) {
        insert(node->left, t);
    } else if (t.linie > node->data.linie || (t.linie == node->data.linie && t.coloana > node->data.coloana)) {
        insert(node->right, t);
    } else {
        node->data.val = t.val;
    }
}

//BC: theta(1)
//WC: thata(log(n))
void deleteNode(Node*& node, int linie, int coloana) {
    if (node == nullptr) {
        return;
    }

    if (linie < node->data.linie || (linie == node->data.linie && coloana < node->data.coloana)) {
        deleteNode(node->left, linie, coloana);
    } else if (linie > node->data.linie || (linie == node->data.linie && coloana > node->data.coloana)) {
        deleteNode(node->right, linie, coloana);
    } else {
        if (node->left == nullptr) {
            Node* temp = node->right;
            delete node;
            node = temp;
        } else if (node->right == nullptr) {
            Node* temp = node->left;
            delete node;
            node = temp;
        } else {
            Node* temp = node->right;
            while (temp->left != nullptr) {
                temp = temp->left;
            }

            node->data = temp->data;

            deleteNode(node->right, temp->data.linie, temp->data.coloana);
        }
    }
}

//BC: theta(1)
//WC: thata(log(n))
TElem Matrice::modifica(int i, int j, TElem e) {
	/* de adaugat */
    if (i < 0 || i >= rand|| j < 0 || j >= col) {
        throw std::exception();
    }
    Node* node = search(root, i, j);

    TElem oldval;
    if(node == nullptr)
        oldval = NULL_TELEMENT;
    else
        oldval = node->data.val;

    if (e != NULL_TELEMENT) {
        Triplet t = { i, j, e };
        insert(root, t);
    } else if(node != nullptr){
        node->data.val = NULL_TELEMENT;
    }
    else if(e == NULL_TELEMENT && node != nullptr)
    {
        deleteNode(node, i, j);
    }

    return oldval;

}

IteratorMatrice Matrice::iterator(int i) const {
    return IteratorMatrice(*this, i);
}

Matrice:: ~Matrice(){
    clear(root);
}




