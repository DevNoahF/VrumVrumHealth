
//Função de criptografia das imagens
function uuidv4() {
  return ([1e7] + -1e3 + -4e3 + -8e3 + -1e11).replace(/[018]/g, (c) =>
    (
      c ^
      (crypto.getRandomValues(new Uint8Array(1))[0] & (15 >> (c / 4)))
    ).toString(16)
  );
}

//Função principal criptografa a imagem e envia para o google cloud
document.getElementById("enviar").addEventListener("click",function(){
  if(verify_data()==false){
    console.log("campo preenchido pó mandar")
  let postid = uuidv4();
  let inputElem = document.getElementById("enviar-anexo");
  let file = inputElem.files[0];

  let blob = file.slice(0, file.size, "image/*");
  newFile = new File([blob], `${postid}_post.jpeg`, { type: "image/*" });

  let formData = new FormData();
  formData.append("enviar-anexo", newFile);
  fetch("/upload", {
    method: "POST",
    body: formData,
  })
    .then((res) => res.text())
    .then(loadPosts());
}
});
// Carrega os posts
function loadPosts() {
  fetch("/upload")
    .then((res) => res.json())
    .then((x) => {
      for (y = 0; y < x[0].length; y++) {
        console.log(x[0][y]);
        const newimg = document.createElement("img");
        newimg.setAttribute(
          "src",
          "https://storage.googleapis.com/perinstorage/" + x[0][y].id
        );
        newimg.setAttribute("width", 50);
        newimg.setAttribute("height", 50);
        document.getElementById("enviar-anexo").appendChild(newimg);
      }
    });
}