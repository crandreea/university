#include <stdlib.h> 
#include <arpa/inet.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netinet/ip.h>
#include <string.h>
#include <unistd.h>
#include <stdio.h>

void deservire_client(int c){
	uint16_t n, list[100], suma;
	recv(c, &n, sizeof(n), MSG_WAITALL);
                n = ntohs(n);

                int i;
                for(i = 0; i<n; i++){
                        recv(c, &list[i], sizeof(list[i]), 0);
                        list[i] = ntohs(list[i]);
                }

                suma = 0;
                for(i = 0; i<n; i++){
                        suma += list[i];
                }
                suma = htons(suma);
                send(c, &suma, sizeof(suma), 0);
                close(c);
}

int main(int argc, char * argv[]){
	if(argc != 3){
		printf("Argumente invalide");
		return 1;
	}

	int port = atoi(argv[2]);
	 const char *ip_address = argv[1];


	int s;
        struct sockaddr_in server, client;
        int c, l;

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
		if(fork()==0){
			deservire_client(c);
			return 0;
		}
	}
}
