#include <stdlib.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netinet/ip.h>
#include <string.h>
#include <unistd.h>
#include <stdio.h>
#include <arpa/inet.h> 

void deservire_client(int c){
	uint16_t n, listn[100], m, listm[100], list[100], cnt;

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

int main(int argc, char* argv[]){

	if(argc != 3){
		printf("Argumente invalide");
		return 1;
	}

        int s;
        struct sockaddr_in server, client;
        int c, l;
                 
	int port = atoi(argv[2]);
	const char* ip_address = argv[1];

        s = socket(AF_INET, SOCK_STREAM, 0);
        if(s<0){
                printf("Eroare la creare socket server\n");
                return 1;
        }
                
        memset(&server, 0, sizeof(server));
        server.sin_port = htons(port);
        server.sin_family = AF_INET;

	if (inet_pton(AF_INET, ip_address, &server.sin_addr.s_addr) <= 0) {
     	   	printf("Eroare: Adresa IP invalidă\n");
        	close(s);
        	return 1;
        }
                
        if(bind(s, (struct sockaddr *)&server, sizeof(server)) < 0){
                printf("Eroare la bind\n");
                return 1;
        }
                                        
        listen(s, 5);
                         
        l = sizeof(client);
        memset(&client, 0, l);
	

	while(1){
		c = accept(s, (struct sockaddr *) &client,(socklen_t *)  &l);
                printf("S-a conectat un client\n");

		if(fork() == 0){
			deservire_client(c);
			return 0;
		}
	}
}
