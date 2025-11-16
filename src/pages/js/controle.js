// controle.js

document.addEventListener("DOMContentLoaded", () => {
  const toggleButton = document.getElementById("toggle-quilometragem");
  const painel = document.getElementById("painel-quilometragem");
  const tabelaBody = painel.querySelector("tbody");

  const btnAdd = painel.querySelector(".action-btn.add");
  const btnEdit = painel.querySelector(".action-btn.edit");
  const btnDelete = painel.querySelector(".action-btn.delete");

  // Alterna o painel de quilometragem
  toggleButton.addEventListener("click", () => {
    painel.classList.toggle("oculto");
    toggleButton.classList.toggle("ativo");
    toggleButton.textContent = painel.classList.contains("oculto")
      ? "Adicionar Nova Entrada ▼"
      : "Fechar painel de Entrada ▲";
  });

  // Função para criar uma linha na tabela
  function adicionarLinha(dados) {
    const tr = document.createElement("tr");
    tr.innerHTML = `
      <td>${dados.data}</td>
      <td>${dados.kmInicial}</td>
      <td>${dados.kmFinal}</td>
      <td>${dados.veiculo}</td>
      <td>${dados.motorista}</td>
      <td>${dados.obs}</td>
    `;
    tabelaBody.appendChild(tr);
  }

  // Botão "Adicionar" — pede os dados via prompt (modo simples)
  btnAdd.addEventListener("click", () => {
    const data = prompt("Data (dd/mm/aaaa):");
    const kmInicial = prompt("Quilometragem inicial:");
    const kmFinal = prompt("Quilometragem final:");
    const veiculo = prompt("Veículo usado:");
    const motorista = prompt("Motorista:");
    const obs = prompt("Observações:");

    if (data && kmInicial && kmFinal && veiculo && motorista) {
      if (tabelaBody.querySelector(".empty")) tabelaBody.innerHTML = ""; // remove texto "Nenhum registro..."
      adicionarLinha({ data, kmInicial, kmFinal, veiculo, motorista, obs });
    } else {
      alert("Preencha todos os campos obrigatórios!");
    }
  });

  // Botão "Editar"
  btnEdit.addEventListener("click", () => {
    const linhas = tabelaBody.querySelectorAll("tr");
    if (!linhas.length || tabelaBody.querySelector(".empty")) {
      alert("Não há registros para editar.");
      return;
    }

    const index = prompt(`Digite o número da linha para editar (1 a ${linhas.length}):`);
    const linha = linhas[index - 1];
    if (!linha) {
      alert("Linha inválida.");
      return;
    }

    const celulas = linha.querySelectorAll("td");
    const novosDados = {
      data: prompt("Nova data:", celulas[0].textContent) || celulas[0].textContent,
      kmInicial: prompt("Nova quilometragem inicial:", celulas[1].textContent) || celulas[1].textContent,
      kmFinal: prompt("Nova quilometragem final:", celulas[2].textContent) || celulas[2].textContent,
      veiculo: prompt("Novo veículo usado:", celulas[3].textContent) || celulas[3].textContent,
      motorista: prompt("Novo motorista:", celulas[4].textContent) || celulas[4].textContent,
      obs: prompt("Novas observações:", celulas[5].textContent) || celulas[5].textContent,
    };

    linha.innerHTML = `
      <td>${novosDados.data}</td>
      <td>${novosDados.kmInicial}</td>
      <td>${novosDados.kmFinal}</td>
      <td>${novosDados.veiculo}</td>
      <td>${novosDados.motorista}</td>
      <td>${novosDados.obs}</td>
    `;
  });

  // Botão "Excluir"
  btnDelete.addEventListener("click", () => {
    const linhas = tabelaBody.querySelectorAll("tr");
    if (!linhas.length || tabelaBody.querySelector(".empty")) {
      alert("Não há registros para excluir.");
      return;
    }

    const index = prompt(`Digite o número da linha para excluir (1 a ${linhas.length}):`);
    const linha = linhas[index - 1];
    if (!linha) {
      alert("Linha inválida.");
      return;
    }

    linha.remove();

    if (!tabelaBody.querySelector("tr")) {
      tabelaBody.innerHTML = `<tr><td colspan="6" class="empty">Nenhum registro cadastrado ainda.</td></tr>`;
    }
  });
});


