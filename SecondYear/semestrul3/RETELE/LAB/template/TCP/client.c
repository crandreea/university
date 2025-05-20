#include <sys/types.h>
#include <sys/socket.h>
#include <stdio.h>
#include <netinet/in.h>
#include <netinet/ip.h>
#include <string.h>
#include <unistd.h>
#include <arpa/inet.h>

int main (int argc, char * argv[]) {

    //partea de citire de argumente server/port
    if(argc != 3){
	printf("Argumente invalide");
	return 1;
    }

    int port = atoi(argv[2]);
    const char *ip_address = argv[1];
   
    int c;
    struct sockaddr_in server;
    uint16_t ...; //declarare variabile

    //creare socket
    c = socket(AF_INET, SOCK_STREAM, 0);
    if (c < 0) {
        printf("Eroare la crearea socketului clientului\n");
        return 1;
    }

    //configurare adrs server
    memset(&server, 0, sizeof(server));
    server.sin_port = htons(port);
    server.sin_family = AF_INET;

    if (inet_pton(AF_INET, ip_address, &server.sin_addr.s_addr) <= 0) {
        printf("Eroare: Adresa IP invalidă\n");
        close(s);
        return 1;
    }
    
    //conect clientului la server
    if (connect(c, (struct sockaddr *) &server, sizeof(server)) <0) {
        printf("Eroare la conectare la server");
        return 1;
    }

    //citire date    
    printf("n = ");
    scanf("%hu", &n);

    //trimitere date la server
    uint16_t x = htons(n);
    send(c, &x, sizeof(x), 0);

    //primire date de la server
    recv(c, &s, sizeof(s), 0);
    s = ntohs(s);

    close(c);
}

