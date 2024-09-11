const form = document.getElementById("agregarForm");
const apiURL = "http://localhost:8080";

form.addEventListener("submit", function (event) {
  event.preventDefault();

  const nroMatricula = document.getElementById("nroMatricula").value;
  const nombre = document.getElementById("nombre").value;
  const apellido = document.getElementById("apellido").value;  
  
  fetch(`${apiURL}/odontologo/guardar`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      nroMatricula,
      nombre,
      apellido      
    }),
  })
    .then((response) => response.json())
    .then((data) => {
      console.log(data);
      mostrarModal("El odontologo fue agregado correctamente" );
      form.reset(); // Resetear el formulario
    })
    .catch((error) => {
      console.error("Error agregando odontologo:", error);
    });
});
