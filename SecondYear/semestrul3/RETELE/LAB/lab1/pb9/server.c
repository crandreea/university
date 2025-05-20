#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netinet/ip.h>
#include <string.h>
#include <unistd.h>
#include <stdio.h>

int main(){
        int s;
        struct sockaddr_in server, client;
        int c, l;

        s = socket(AF_INET, SOCK_STREAM, 0);
        if(s<0){
                printf("Eroare la creare socket server\n");
                return 1;
        }

        memset(&server, 0, sizeof(server));
        server.sin_port = htons(1234);
        server.sin_family = AF_INET;
        server.sin_addr.s_addr = INADDR_ANY;

        if(bind(s, (struct sockaddr *)&server, sizeof(server)) < 0){
                printf("Eroare la bind\n");
                return 1;
        }

        listen(s, 5);

        l = sizeof(client);
        memset(&client, 0, l);

        while(1){
                uint16_t n, listn[100], m, listm[100], list[100], cnt;
                c = accept(s, (struct sockaddr *) &client,(socklen_t *)  &l);
                printf("S-a conectat un client\n");

                recv(c, &n, sizeof(n), MSG_WAITALL);
                n = ntohs(n);

                int i;
                for(i = 0; i<n; i++){
                        recv(c, &listn[i], sizeof(listn[i]), 0);
                        listn[i] = ntohs(listn[i]);
                }

		recv(c, &m, sizeof(m), MSG_WAITALL);
		m = ntohs(m);

		int j;
		for(j = 0; j<m; j++){
			recv(c, &listm[j], sizeof(listm[j]), 0);
			listm[j] = ntohs(listm[j]);
		}

		cnt = 0;
		for(i = 0; i<n; i++){
			int sw = 0;
			for(j = 0; j<m; j++){
				if(listn[i] == listm[j]){
					sw = 1;
				}
			}
			if (sw == 0){
				list[cnt] = listn[i];
				cnt++;	
			}
		}

                uint16_t cnt_net = htons(cnt);
		send(c, &cnt_net, sizeof(cnt_net), 0);

		for(i = 0; i<cnt; i++){
			uint16_t elem  = htons(list[i]);
			send(c, &elem, sizeof(elem), 0);
		}
                close(c);
        }
}

