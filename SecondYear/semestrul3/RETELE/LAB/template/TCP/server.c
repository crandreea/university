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
	uint16_t ...;//initializare

	//primesc de la client
	recv(c, &n, sizeof(n), MSG_WAITALL);
        n = ntohs(n);

	//calculez ce trebuie pentru client
        //trimit la client raspunsul
        s = htons(s);
        send(c, &s, sizeof(s), 0);
        close(c);
}

int main(int argc, char * argv[]){
	
	//ip/port ca si argumente
	if(argc != 3){
		printf("Argumente invalide");
		return 1;
	}

	int port = atoi(argv[2]);
	const char *ip_address = argv[1];


	int s;
        struct sockaddr_in server, client;
        int c, l;

	//creare socket
        s = socket(AF_INET, SOCK_STREAM, 0);
        if(s<0){
                printf("Eroare la creare socket server\n");
                return 1;
        }
	
	//initializare adrs/port server
        memset(&server, 0, sizeof(server));
        server.sin_port = htons(port);
        server.sin_family = AF_INET;
        
	if (inet_pton(AF_INET, ip_address, &server.sin_addr.s_addr) <= 0) {
        	printf("Eroare: Adresa IP invalidă\n");
        	close(s);
        	return 1;
    	}

	//bind dintre socket si server
        if(bind(s, (struct sockaddr *)&server, sizeof(server)) < 0){
                printf("Eroare la bind\n");
                return 1;
        }

        listen(s, 5); //nr de clienti in asteptare

        l = sizeof(client);
        memset(&client, 0, l);

	while(1){
		//conectare cu client 
		c = accept(s, (struct sockaddr *) &client,(socklen_t *)  &l);
                printf("S-a conectat un client\n");
		
		//server concurent si fara fork pt server iterativ
		if(fork()==0){
			deservire_client(c);
			return 0;
		}
	}
}

