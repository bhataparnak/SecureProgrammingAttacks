# SecureProgrammingAttacks

## SEED Attack Labs

These labs cover some of the most common vulnerabilities and attacks exploiting these vulnerabilities. All the labs are presented in the form of PDF files, containing some screenshots.

## Getting Started

These instructions will get you to set up the environment on your local machine to perform these attacks.

Step 1: Create a new VM in Virtual Box.

Step 2: Download the image SEEDUbuntu-16.04-32bit.zip from [here](https://seedsecuritylabs.org/lab_env.html).

Step 3: Use the Virtual Machine Hard Disk file to setup your VM.

Step 4: Configure the VM.

The [link](https://seedsecuritylabs.org/Labs_16.04/Documents/SEEDVM_VirtualBoxManual.pdf) contains a document that can be used to set up the VM without any issues.

## Motivation

The labs were completed as a part of the Secure Programming (CSE 5382) course at the University of Texas at Arlington. The course is well structured to understand the concepts of secure programming.

## List of Attacks

### 1. Environment Variable and Set-UID Vulnerability

Set-UID is an important security mechanism in Unix operating systems. When a Set-UID program runs, it assumes the owner's privileges. For example, if the program's owner is root, then when anyone runs this program, the program gains the root's privileges during its execution. This can be very well exploited, as seen in the lab. The lab also demonstrates the effect of environment variables on the behavior of Set-UID programs.

### 2. Shellshock Attack

In this attack we launched the shellshock attack on a remote web server and then gained the reverse shell by exploiting the vulnerability.

### 3. Buffer Overflow Vulnerability

Buffer overflow is defined as the condition in which a program attempts to write data beyond the boundaries of pre-allocated fixed-length buffers. This vulnerability can be utilized by a malicious user to alter the flow control of the program, even execute arbitrary pieces of code. The task in this lab is to develop a scheme to exploit the buffer overflow vulnerability and finally gain the root privilege.

### 4. Return-to-libc Attack Lab

This attack can bypass an existing protection scheme currently implemented in major Linux operating systems. A common way to exploit a buffer-overflow vulnerability is to overflow the buffer with a malicious shellcode, and then cause the vulnerable program to jump to the shellcode stored in the stack. To prevent these types of attacks, some operating systems allow programs to make their stacks non-executable; therefore, jumping to the shellcode causes the program to fail.

This lab covers the following topics:

• Buffer overflow vulnerability

• Stack layout in a function invocation and Non-executable stack

• Return-to-libc attack and Return-Oriented Programming (ROP)

### 5. Format String Vulnerability

Description: The format-string vulnerability is caused by code like printf(user input), where the contents of the variable of user input are provided by users. When this program is running with privileges (e.g., Set-UID program), this printf statement becomes dangerous, because it can lead to one of the following consequences: (1) crash the program, (2) read the internal memory of the program, (3) modify the internal memory of the program, and most severely, (4) inject and execute malicious code using the victim program’s
privilege. This lab covers the following topics:

• Format string vulnerability

• Code injection

• Shellcode

• Reverse shell

### 6. Race Condition Vulnerability

A race condition occurs when multiple processes access and manipulate the same data concurrently, and the outcome of the execution depends on the particular order in which the access takes place. If a privileged program has a race-condition vulnerability, attackers can run a parallel process to “race” against the privileged program, with an intention to change the behaviors of the program. The task is to exploit this vulnerability and gain root privilege.

This lab covers the following topics:

• Race condition vulnerability

• Sticky symlink protection

• Principle of least privilege

### 7. Cross-Site Request Forgery (CSRF) Attack

In this lab, we will be attacking a social networking web application using the CSRF attack. The open-source social networking application called Elgg has countermeasures against CSRF, but we have turned them off for this lab. We also study the most common countermeasures of this attack.

This lab covers the following topics:

• Cross-Site Request Forgery attack

• CSRF countermeasures: Secret token and Same-site cookie

• HTTP GET and POST requests

• JavaScript and Ajax

### 8. Cross Site Scripting (XSS) Attack

In this lab, we need to exploit this vulnerability to launch an XSS attack on the modified Elgg, in a way that is similar to what Samy Kamkar did to MySpace in 2005 through the notorious Samy worm. The ultimate goal of this attack is to spread an XSS worm among the users, such that whoever views an infected user profile will be infected, and whoever is infected will add you (i.e., the attacker) to his/her friend list.

### 9. SQL injection Attack

In this lab, we have created a web application that is vulnerable to the SQL injection attack. Our web application includes the common mistakes made by many web developers. Our goal is to find ways to exploit the SQL injection vulnerabilities, demonstrate the damage that can be achieved by the attack, and master the techniques that can help defend against such type of attacks.

### 10. Understanding and Using Static Code Analysis Tools

The goal of this assignment is to familiarize you with using open source static code analysis tools to find bugs and vulnerabilities in source code and to recognize that not all tools are created equal. Each tool uses different analysis techniques and is programmed with different rules for detection of problems. Also, the settings, and in some cases the plugins, used in a tool will influence what types of problems it can identify.

### 11. Input Validation

The goal of this assignment is to produce a program that validates its input using regular

### Capture the Flag

expressions



