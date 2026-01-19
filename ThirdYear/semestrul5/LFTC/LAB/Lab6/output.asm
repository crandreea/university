section .data
format_in  db "%d", 0
format_out db "%d", 10, 0

section .bss
v_z resq 1
v_y resq 1
v_x resq 1

section .text
global _main
extern _printf, _scanf

_main:
    push rbp
    mov rbp, rsp
    lea rdi, [rel format_in]
    lea rsi, [rel v_x]
    xor eax, eax
    call _scanf
    lea rdi, [rel format_in]
    lea rsi, [rel v_y]
    xor eax, eax
    call _scanf
    push qword [rel v_x]
    push qword [rel v_y]
    pop rbx
    pop rax
    imul rax, rbx
    push rax
    push 2
    pop rbx
    pop rax
    add rax, rbx
    push rax
    pop rax
    mov [rel v_z], rax
    lea rdi, [rel format_out]
    mov rsi, [rel v_z]
    xor eax, eax
    call _printf
    mov rax, 0
    leave
    ret
