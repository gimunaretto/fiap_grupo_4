# FIAP: Trabalho de Avaliação - Java
- ENUNCIADO: Criar um bot no telegram para interagir com o usuário;
## bot-telegram
. botUserName: **_@WendyPlusBot_**

![](wendy.png)


#

1. Criado projeto com o MAVEN:  
   mvn archetype:generate (escolher o archetype sugerido)

   - _groupId_, Value: fiap.java;
   - _artifactId_, Value: bot-telegram;
   - _version_, Value: 1.0-SNAPSHOT;
   - _package_, Value: fiap.1scjr;

2. Classes auxiliares da Classe Principal (main):
   - _Config.java_ - fornece token e id do bot;
   - _CommandsBot.java_ - classe que extende TelegramLongPollingBot, para iterações com usuário;

3. Utiizamos:
   - Utilizado Máquina de Estado simples para conversas sobre Filmes e a resposta seguinte atrelada ao Gênero;
   - Reconhecimento de mensagens via REGEX;
   - Informações sobre o clima, dia, hora, e idade!
   - Uso do Markdown;
   - Uso do Javadoc;
   - Projeto feito utilizando o GitHub;



**consultado**:  
_https://monsterdeveloper.gitbook.io/java-telegram-bot-tutorial/_

**equipe:**  
- DANIEL DA CUNHA NOBREGA  
- GIULIANA FERNANDES MUNARETTO  
- PEDRO SANTANA  
- ROBERTO GUEDES GARRONES  
- JORGE ROSIVAN RODRIGUES BATISTA 

