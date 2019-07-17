# autorama_inteligente
Projeto de redes de computadores. Curso de Engenharia de computação. 
Construção de um sistema de cadastro e supervisão para usuários de um briquedo autorama, no qual é possível analisar em tempo
real a corrida dos participantes, qualificação dos carros e tempo recorde de maior volta. Isto através de um app.java instalado
em um celular, tablet ou computador.
O circuito do autorama possui um sensor RFID ao qual retorna informações dos carros que passam por ele. Essas informaços são
retiradas por um Raspberry PI Zero W e enviadas a um servidor local para analise e demonstração ao usuário cliente. 

Autores: Gabriel Sá Barreto Alves e Samuel Vitorio Lima

Passos para execução do sistema:

	1ª - Execute o arquivo Multiclient.java do projeto multiclient para startar o servidor.
	2ª - Execute o arquivo TelaPrincipal.java para logar como administrador (user: Admin, Senha: admin123).
	3ª - No projeto ClientCorrida, execute o arquivo ClientRace.java localizado no pacote view.
	4ª - Como administrador, realize cadastro de carros e pilotos.
	5ª - Como administrador, realize cadastro de corrida com a quantidade de voltas.
	6ª - Como administrador, associe os pilotos aos carros e confirme cada um deles.
	7ª - Como administrador, depois vá na tela de gerenciar corrida e clique em começar.
	8ª - Abra o projeto SimuladorSensor e execute o arquivo GeradorPacotesSensor.java dentro do pacote gerador.

Observações: 
	1ª - É necessário instalar o netbeans e o jdk do java para rodar o projeto na IDE.
	2ª - Caso for executar cada aplicação em um computador diferente é necesário mudar o ip que cada cliente irá se conectar. Mudar no projeto ClientCorrida, ClienteAdministrador e o SimuladorSensor.
	3ª - Foi gerado o javadoc dos clientes e do server , procurar no diretorio /dist/javadoc de cada client e o server




	 - 
