var exportar = document.getElementById("exportar")


//Função que permite exportar tabela para pdf
exportar.addEventListener("click",function(){
   const { jsPDF } = window.jspdf;
    const doc = new jsPDF({
      orientation: 'landscape', 
      unit: 'pt',
      format: 'a4'
    });

    // Seleciona a tabela toda
    const tableElement = document.querySelector('.solicitacoes-table');

    // Permite editar a base do PDF para qualquer forma
    doc.autoTable({
      html: tableElement,
      startY: 40, 
      theme: 'striped', 
      headStyles: { fillColor: [41, 128, 185] },
      styles: { fontSize: 10 },

    });

    doc.save('Solicitações Aprovadas.pdf')
})