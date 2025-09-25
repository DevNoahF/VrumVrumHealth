//NOTA:Estudar mais js, minha cabeça ficou confusa criando esse código mesmo tendo visto ele a 5 minutos atrás-perin

document.addEventListener("DOMContentLoaded", () => {//Literalmente tudo aqui é temporário, mas serve pra pegar os dados do forms, criar a tabela e inserir
  var form = document.getElementById("form_adiciona");
  var botao = document.getElementById("btnAdicionar");
  var tabela = document.getElementById("tbodySolicitacoes");

  // Adiciona evento no botão
  botao.addEventListener("click", function(event) {
    event.preventDefault();


    //variáveis pegam os valores do forms
    var id = form.id.value;
    var nome = form.nome.value; 
    var endereco = form.endereco.value;
    var bairro = form.bairro.value;
    var cidade = form.cidade.value;
    var complemento = form.complemento.value;
    var unidade = form.unidade.value;
    var data = form.data.value;

    // Uma Tr nova precisa ser criada sempre que alguém aperta o botão
    var novaLinha = document.createElement("tr");


    
    // td são as tags que vão armazenar os valores dos forms
    var idTD= document.createElement("td");//cria uma td
    idTD.textContent=id;//adicionar o conteúdo do forms a td
    novaLinha.appendChild(idTD);//adiciona a td no nossa linha ou tr

    var nomeTd = document.createElement("td");
    nomeTd.textContent = nome;  
    novaLinha.appendChild(nomeTd);

    var enderecoTd = document.createElement("td");
    enderecoTd.textContent = endereco;
    novaLinha.appendChild(enderecoTd);

    var bairroTd = document.createElement("td");
    bairroTd.textContent = bairro;
    novaLinha.appendChild(bairroTd);

    var cidadeTd = document.createElement("td");
    cidadeTd.textContent = cidade;
    novaLinha.appendChild(cidadeTd);

    var complementoTd = document.createElement("td");
    complementoTd.textContent = complemento;
    novaLinha.appendChild(complementoTd);

    var unidadeTd = document.createElement("td");
    unidadeTd.textContent = unidade;
    novaLinha.appendChild(unidadeTd);

    var dataTd = document.createElement("td");
    dataTd.textContent = data;
    novaLinha.appendChild(dataTd);
    //Existe alguma forma mais simplificada de criar essas tds?
    


    tabela.appendChild(novaLinha);//adiciona toda a nova linha com as informações


    form.reset();//deixa o formulário vazio 
  });
});
