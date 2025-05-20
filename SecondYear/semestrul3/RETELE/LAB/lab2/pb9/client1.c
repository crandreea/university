#include <stdlib.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <stdio.h>
#include <netinet/in.h>
#include <netinet/ip.h>
#include <string.h>
#include <unistd.h>
#include <arpa/inet.h>

int main (int argc, char * argv[]) {
	if(argc != 3){
		printf("Argumente invalide!");
		return 1;
	}
	 struct sockaddr_in server;
	int c;
	int port = atoi(argv[2]);
	const char* ip_address = argv[1];

	 if (inet_pton(AF_INET, ip_address, &server.sin_addr.s_addr) <= 0) {
                printf("Eroare: Adresa IP invalidă\n");
                close(c);
                return 1;
        }

    uint16_t n,listn[100],m, listm[100], cnt, list[100];

    c = socket(AF_INET, SOCK_STREAM, 0);
    if (c < 0) {
        printf("Eroare la crearea socketului clientului\n");
        return 1;
    }

    memset(&server, 0, sizeof(server));
    server.sin_port = htons(port);
    server.sin_family = AF_INET;

    if (connect(c, (struct sockaddr *) &server, sizeof(server)) <0) {
        printf("Eroare la conectare la server");
        return 1;
    }

    printf("n = ");
    scanf("%hu", &n);
    int i;
    printf("Introduceti %hu numere: ", n);
    for(i = 0; i<n; i++){
        scanf("%hu", &listn[i]);
    }

    printf("m = ");
    scanf("%hu", &m);
    printf("Introduceti %hu numere: ", m);
    for(i = 0; i<m; i++){
        scanf("%hu", &listm[i]);
    }

    uint16_t x = htons(n);
    send(c, &x, sizeof(x), 0);

    for(i = 0; i<n; i++){
        uint16_t x = htons(listn[i]);
        send(c, &x, sizeof(x), 0);
    }

    uint16_t y = htons(m);
    send(c, &y, sizeof(y), 0);

    for(i = 0; i<m; i++){
        uint16_t y = htons(listm[i]);
        send(c, &y, sizeof(y), 0);
    }

    recv(c, &cnt, sizeof(cnt), 0);
    cnt = ntohs(cnt);

    for(i = 0; i<cnt; i++){

        recv(c, &list[i], sizeof(list[i]), 0);
        list[i] = ntohs(list[i]);
    }
        
    printf("Diferenta listelor este: ");
    for(i = 0; i<cnt; i++){
        printf("%hu ", list[i]);
    }
    close(c);
}
