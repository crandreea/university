#include <arpa/inet.h>   // For inet_addr
#include <unistd.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <stdio.h>
#include <netinet/in.h>
#include <netinet/ip.h>
#include <string.h>

int is_palindrom(uint16_t num) {
    int aux = num;
    int ogl = 0;
    int c;
    while(num != 0){
	c = num % 10;
	ogl = ogl * 10 + c;
	num /= 10;
    }

    if(aux == ogl){
	 return 1;
    }
    else{
    return 0;
    }
}


int main() {
  int s;
  struct sockaddr_in server, client;
  int c, l, i;
  
  s = socket(AF_INET, SOCK_DGRAM, 0);
  if (s < 0) {
    printf("Eroare la crearea socketului server\n");
    return 1;
  }
  
  memset(&server, 0, sizeof(server));
  server.sin_port = htons(1234);
  server.sin_family = AF_INET;
  server.sin_addr.s_addr = INADDR_ANY;
  
  if (bind(s, (struct sockaddr *) &server, sizeof(server)) < 0) {
    printf("Eroare la bind\n");
    return 1;
  }
 
  l = sizeof(client);
  memset(&client, 0, sizeof(client));
  
  while(1){
 	 uint16_t n, list[100];

  	recvfrom(s, &n, sizeof(n), MSG_WAITALL, (struct sockaddr *) &client,(socklen_t *) &l);
  	n = ntohs(n);
	
        int k = 0, aux1 = 10000;
	while(k<n){
                if(is_palindrom(aux1) == 1){
                        list[k] = aux1;
                        k ++;
                }
                aux1 = aux1 + 1;
        }

	for(int i = 0; i<=n; i++){
		list[i] = htons(list[i]);
        	sendto(s, &list[i], sizeof(list[i]), 0, (struct sockaddr *) &client, l);
	}  	
  }     

  close(s);	
}

