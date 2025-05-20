#include <arpa/inet.h>   // For inet_addr
#include <unistd.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <stdio.h>
#include <netinet/in.h>
#include <netinet/ip.h>
#include <string.h>
 
int main() {
  int c;
  struct sockaddr_in server;
  uint16_t n, list[100];
  socklen_t server_len = sizeof(server);
  
  c = socket(AF_INET, SOCK_DGRAM, 0);
  if (c < 0) {
    printf("Eroare la crearea socketului client\n");
    return 1;
  }
  
  memset(&server, 0, sizeof(server));
  server.sin_port = htons(1234);
  server.sin_family = AF_INET;
  server.sin_addr.s_addr = inet_addr("127.0.0.1");
  
  printf("n = ");
  scanf("%hu", &n);

  uint16_t x = htons(n);
  sendto(c, &x, sizeof(x), 0,  (struct sockaddr*) & server, sizeof(server));
  
  for(int i = 0; i<n; i++){
	 recvfrom(c, &list[i], sizeof(list[i]), 0,  (struct sockaddr*) & server, &server_len);
  	 list[i]  = ntohs(list[i]);
  }

  printf("Primele %hu numere palindrom cu cel putin 5 cifre sunt:", n);
  for(int i = 0; i<n; i++){
	printf("%hu", list[i]);
	printf(" ");
  }
  close(c);
}

