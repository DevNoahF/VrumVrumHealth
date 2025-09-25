//NOTA:Estudar mais js, minha cabeça ficou confusa criando esse código mesmo tendo visto ele a 5 minutos atrás-perin

document.addEventListener("DOMContentLoaded", () => {//Literalmente tudo aqui é temporário, mas serve pra pegar os dados do forms, criar a tabela e inserir
  var form = document.getElementById("form_adiciona");
  var botao = document.getElementById("btnAdicionar");
  var tabela = document.getElementById("tbodySolicitacoes");

  // Adiciona evento no botão
  botao.addEventListener("click", function(event) {
    event.preventDefault();


    //variáveis pegam os valores do forms
    const id = form.id.value;
    const nome = form.nome.value; 
    const endereco = form.endereco.value;
    const bairro = form.bairro.value;
    const cidade = form.cidade.value;
    const complemento = form.complemento.value;
    const unidade = form.unidade.value;
    const data = form.data.value;

    // Uma Tr nova precisa ser criada sempre que alguém aperta o botão
    const novaLinha = document.createElement("tr");


    
    // td são as tags que vão armazenar os valores dos forms
    const idTD= document.createElement("td");//cria uma td
    idTD.textContent=id;//adicionar o conteúdo do forms a td
    novaLinha.appendChild(idTD);//adiciona a td no nossa linha ou tr

    const nomeTd = document.createElement("td");
    nomeTd.textContent = nome;  
    novaLinha.appendChild(nomeTd);

    const enderecoTd = document.createElement("td");
    enderecoTd.textContent = endereco;
    novaLinha.appendChild(enderecoTd);

    const bairroTd = document.createElement("td");
    bairroTd.textContent = bairro;
    novaLinha.appendChild(bairroTd);

    const cidadeTd = document.createElement("td");
    cidadeTd.textContent = cidade;
    novaLinha.appendChild(cidadeTd);

    const complementoTd = document.createElement("td");
    complementoTd.textContent = complemento;
    novaLinha.appendChild(complementoTd);

    const unidadeTd = document.createElement("td");
    unidadeTd.textContent = unidade;
    novaLinha.appendChild(unidadeTd);

    const dataTd = document.createElement("td");
    dataTd.textContent = data;
    novaLinha.appendChild(dataTd);
    //Existe alguma forma mais simplificada de criar essas tds?
    


    tabela.appendChild(novaLinha);//adiciona toda a nova linha com as informações


    form.reset();//deixa o formulário vazio 
  });
});
