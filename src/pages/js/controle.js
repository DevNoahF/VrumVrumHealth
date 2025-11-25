import { getToken } from "./submit.js";

(async () => {
  let authToken;
  try {
    authToken = await getToken();
  } catch (err) {
    console.error("Erro ao obter token:", err);
    authToken = null;
  }

  function getAuthHeaders() {
    return {
      "Content-Type": "application/json",
      "Authorization": authToken ? `Bearer ${authToken}` : ""
    };
  }

  function init() {
    const toggleButton = document.getElementById("toggle-quilometragem");
    const painel = document.getElementById("painel-quilometragem");
    const tabelaBody = painel.querySelector("tbody");
    const form = document.getElementById("form-quilometragem");
    const inputData = form.querySelector("#data");
    const inputKmInicial = form.querySelector("#kmInicial");
    const inputKmFinal = form.querySelector("#kmFinal");
    const selectVeiculo = form.querySelector("#veiculo");
    const textareaObs = form.querySelector("#obs");
    const btnIniciar = document.getElementById("btn-iniciar");
    const btnFinalizar = document.getElementById("btn-finalizar");
    const btnAdd = painel.querySelector(".action-btn.add");
    const btnEdit = painel.querySelector(".action-btn.edit");
    const btnDelete = painel.querySelector(".action-btn.delete");

    console.log("Init: toggleButton =", toggleButton, "painel =", painel);

    if (!toggleButton || !painel) {
      console.error("toggleButton ou painel não encontrados no DOM — verifique seus IDs.");
      return;
    }

    // Toggle do painel
    toggleButton.addEventListener("click", () => {
      painel.classList.toggle("oculto");
      toggleButton.classList.toggle("ativo");
      toggleButton.textContent = painel.classList.contains("oculto")
        ? "Adicionar Nova Entrada ▼"
        : "Fechar painel de Entrada ▲";
    });

    // Carregar motoristas e veículos da API
    async function carregarMotoristasEVeiculos() {
      try {
        const [respMotoristas, respVeiculos] = await Promise.all([
          fetch("http://localhost:8080/veiculo", { headers: getAuthHeaders() })
        ]);

        if (!respVeiculos.ok) {
          throw new Error("Erro ao buscar motoristas ou veículos");
        }

        const veiculos = await respVeiculos.json();


        selectVeiculo.innerHTML = `<option value="">-- selecione veículo --</option>`;



        veiculos.forEach(v => {
          const opt = document.createElement("option");
          opt.value = v.id;
          opt.textContent = v.modelo || v.nome || `Veículo ${v.id}`;
          selectVeiculo.appendChild(opt);
        });
      } catch (err) {
        console.error("Erro ao carregar motoristas/veículos:", err);
        alert("Não foi possível carregar motoristas ou veículos.");
      }
    }

    carregarMotoristasEVeiculos();

    function clearRowSelection() {
      const prev = tabelaBody.querySelector("tr.selected");
      if (prev) prev.classList.remove("selected");
    }

    function adicionarLinha(dados) {
      const empty = tabelaBody.querySelector(".empty");
      if (empty) {
        empty.parentElement.remove();
      }

      const tr = document.createElement("tr");
      if (dados.id != null) {
        tr.dataset.id = String(dados.id);
      }

      tr.innerHTML = `
        <td>${dados.data ?? ""}</td>
        <td>${dados.kmInicial ?? ""}</td>
        <td>${dados.kmFinal ?? ""}</td>
        <td>${dados.veiculoNome ?? dados.veiculo ?? ""}</td>
        <td>${dados.motoristaNome ?? dados.motorista ?? ""}</td>
        <td>${dados.obs ?? ""}</td>
      `;

      tr.addEventListener("click", () => {
        clearRowSelection();
        tr.classList.add("selected");

        inputData.value = dados.data || "";
        inputKmInicial.value = dados.kmInicial != null ? dados.kmInicial : "";
        inputKmFinal.value = dados.kmFinal != null ? dados.kmFinal : "";
        textareaObs.value = dados.obs ?? "";

        if (dados.veiculoId != null) {
          selectVeiculo.value = dados.veiculoId;
        } else if (dados.veiculo) {
          for (let opt of selectVeiculo.options) {
            if (opt.text === dados.veiculo || opt.text === dados.veiculoNome) {
              selectVeiculo.value = opt.value;
              break;
            }
          }
        }

        if (dados.motoristaId != null) {
          selectMotorista.value = dados.motoristaId;
        } else if (dados.motorista) {
          for (let opt of selectMotorista.options) {
            if (opt.text === dados.motorista || opt.text === dados.motoristaNome) {
              selectMotorista.value = opt.value;
              break;
            }
          }
        }
      });

      tabelaBody.appendChild(tr);
    }

    btnIniciar.addEventListener("click", async () => {
      const dados = {
        data: inputData.value,
        kmInicial: Number(inputKmInicial.value),
        kmFinal: Number(inputKmFinal.value),
        veiculoId: selectVeiculo.value,
        motoristaId: sessionStorage.getItem("motoristaId"),//Pega o id com base na sessão
        obs: textareaObs.value
      };

      if (!dados.data || isNaN(dados.kmInicial) || !dados.veiculoId || !dados.motoristaId) {
        alert("Preencha os campos obrigatórios.");
        return;
      }

      try {
        const resp = await fetch("http://localhost:8080/diarioBordo", {
          method: "POST",
          headers: getAuthHeaders(),
          body: JSON.stringify(dados)
        });
        if (!resp.ok) throw new Error("Erro no POST: " + resp.status);

        const resultado = await resp.json();
        adicionarLinha(resultado);

        form.reset();
        inputKmFinal.value = "0";
      } catch (err) {
        console.error("Erro ao iniciar entrada:", err);
        alert("Falha ao iniciar entrada.");
      }
    });

    btnFinalizar.addEventListener("click", async () => {
      const linhaSelecionada = tabelaBody.querySelector("tr.selected");
      let id = linhaSelecionada ? linhaSelecionada.dataset.id : null;
      if (!id) {
        id = prompt("Digite o ID da entrada para finalizar:");
      }
      if (!id) {
        alert("ID é necessário para finalizar.");
        return;
      }

      const dados = {
        data: inputData.value,
        kmInicial: Number(inputKmInicial.value),
        kmFinal: Number(inputKmFinal.value),
        veiculoId: selectVeiculo.value,
        motoristaId: selectMotorista.value,
        obs: textareaObs.value
      };

      try {
        const resp = await fetch(`http://localhost:8080/diarioBordo/${id}`, {
          method: "PATCH",
          headers: getAuthHeaders(),
          body: JSON.stringify(dados)
        });
        if (!resp.ok) throw new Error("Erro no PATCH: " + resp.status);

        const resultado = await resp.json();
        if (linhaSelecionada) linhaSelecionada.remove();
        adicionarLinha(resultado);

        form.reset();
        inputKmFinal.value = "0";
      } catch (err) {
        console.error("Erro ao finalizar entrada:", err);
        alert("Falha ao finalizar entrada.");
      }
    });

    btnAdd.addEventListener("click", () => {
      const data = inputData.value;
      const kmInicial = inputKmInicial.value;
      const kmFinal = inputKmFinal.value;
      const veiculo = selectVeiculo.options[selectVeiculo.selectedIndex]?.text || "";
      const motorista = selectMotorista.options[selectMotorista.selectedIndex]?.text || "";
      const obs = textareaObs.value;

      if (!data || !kmInicial || !veiculo || !motorista) {
        alert("Preencha todos os campos obrigatórios para adicionar localmente.");
        return;
      }

      adicionarLinha({ data, kmInicial, kmFinal, veiculo, motorista, obs });
      form.reset();
      inputKmFinal.value = "0";
    });

    btnEdit.addEventListener("click", () => {
      const linhaSelecionada = tabelaBody.querySelector("tr.selected");
      if (!linhaSelecionada) {
        alert("Clique na linha que deseja editar para selecioná-la.");
        return;
      }
      alert("Formulário preenchido. Edite os valores e clique em Finalizar para salvar.");
    });

    btnDelete.addEventListener("click", () => {
      const linhaSelecionada = tabelaBody.querySelector("tr.selected");
      if (!linhaSelecionada) {
        alert("Selecione a linha que deseja excluir clicando nela.");
        return;
      }

      if (!confirm("Deseja realmente excluir esta entrada?")) {
        return;
      }

      linhaSelecionada.remove();
      if (!tabelaBody.querySelector("tr")) {
        tabelaBody.innerHTML = `<tr><td colspan="6" class="empty">Nenhum registro cadastrado ainda.</td></tr>`;
      }
    });

    // Se a tabela estiver vazia, adiciona a linha “nenhum registro”
    if (!tabelaBody.querySelector("tr")) {
      tabelaBody.innerHTML = `<tr><td colspan="6" class="empty">Nenhum registro cadastrado ainda.</td></tr>`;
    }
  }

  if (document.readyState === "loading") {
    document.addEventListener("DOMContentLoaded", init);
  } else {
    init();
  }
})();
