#include <arpa/inet.h>   
#include <unistd.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <stdio.h>
#include <netinet/in.h>
#include <netinet/ip.h>
#include <string.h>

int main() {
  int s;
  struct sockaddr_in server, client;
  int c, l, i;
  uint16_t  old = 0;

  //creare socket cu SOCK_DGRAM  
  s = socket(AF_INET, SOCK_DGRAM, 0);
  if (s < 0) {
    printf("Eroare la crearea socketului server\n");
    return 1;
  }
  
  //initializare date server
  memset(&server, 0, sizeof(server));
  server.sin_port = htons(1234);
  server.sin_family = AF_INET;
  server.sin_addr.s_addr = INADDR_ANY;
  
  //bind socket si server
  if (bind(s, (struct sockaddr *) &server, sizeof(server)) < 0) {
    printf("Eroare la bind\n");
    return 1;
  }
 
  l = sizeof(client);
  memset(&client, 0, sizeof(client));
  
  while(1){
 	 uint16_t n, k;
	
	//primesc date de la client
  	recvfrom(s, &n, sizeof(n), MSG_WAITALL, (struct sockaddr *) &client,(socklen_t *) &l);
  	n = ntohs(n);
	
	//trimit date la client 
  	k = htons(k);
  	sendto(s, &k, sizeof(k), 0, (struct sockaddr *) &client, l);
  }     

  close(s);	
}
