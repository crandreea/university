#include <iostream>
#include <fstream>
using namespace std;

const int INF = 1e9;
//in alg lui prim se adauga noduri in arbore alegand mereu muchia de cost minim pana cand toate nodurile au fost adaugate
//exemplu unde poate fi utilizat in viata reala: transportul intr un mod eficient (din punct de vedere al banilor sau al cosumului)
//                                               de la un obiectiv turistic la altul

int main()
{
    ifstream inf("prim.in");
    ofstream outf("prim.out");
    int n,m;

    inf>>n>>m;
    int a[n][n];
    for(int i=0;i<n;++i)
        for(int j=0;j<n;++j)
            a[i][j]=INF; //matricea (inca nu sunt leg intre noduri)

    int x,y,c;
    for(int i=0;i<m;++i)
    {
        inf>>x>>y>>c;
        x--; y--;
        a[x][y]=a[y][x]=c; //se adauga costul dintre muchii in matrice
    }

    bool vizitat[n]; //vector pentru a vedea daca un nod a fost vizitat
    int cost=0; //costul minim
    int dist[n]; //distanta de la nodul sursa la toate nodurile
    int t[n]; //vect tati
    for(int i=0;i<n;++i)
    {
        vizitat[i]=false;
        t[i]=0;
        dist[i]=INF;
    }

    t[0]=-1; //nodul sursa = 0 si nu are tata
    dist[0]=0;
    for(int i=0;i<n;++i)
    {
        int p=-1;//pt indexul nodului nevizitat
        int Minim=INF;
        //gasesc nodul nevizitat cu costul cel mai mic
        for(int j=0;j<n;++j)
        {
            if(!vizitat[j] && dist[j]<Minim)
            {
                Minim=dist[j];
                p=j;
            }
        }

        if(p!=-1)//am gasit nodul nevizitat cu distanta cea mia mica
        {
            vizitat[p]=true; //am vizitat nodul si actualizez distanta si tatal
            for(int j=0;j<n;++j)
            {
                if(!vizitat[j] && dist[j]>a[p][j])
                {
                    dist[j]=a[p][j];
                    t[j]=p;
                }
            }
            //creste costul
            cost+=dist[p];
        }
    }

    outf<<cost<<"\n"; //costul minim
//    for(int i=0;i<n;++i)
//    {
//        outf<<t[i]+1<<" "; //vectorul de tati
//    }

    return 0;
}
