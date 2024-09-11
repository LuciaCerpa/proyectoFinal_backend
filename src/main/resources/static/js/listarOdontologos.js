const apiURL = "http://localhost:8080";

const tableBody = document.querySelector("#odontologoTable tbody");
const editModal = new bootstrap.Modal(document.getElementById("editModal"));
const editForm = document.getElementById("editForm");
let currentOdontologoId;

function fetchOdontologos() {
  fetch(`${apiURL}/odontologo/buscartodos`)
    .then((response) => response.json())
    .then((data) => {
      console.log(data);
      tableBody.innerHTML = "";

      data.forEach((odontologo, index) => {
        const row = document.createElement("tr");

        row.innerHTML = `
        <tr class="table-light">
              <td>${index+1}</td>
              <td>${odontologo.nroMatricula}</td>              
              <td>${odontologo.nombre}</td>              
              <td>${odontologo.apellido}</td>    
              <td>
                <button class="btn btn-outline-primary btn-sm" onclick="editOdontologo(${odontologo.id}, '${odontologo.nroMatricula}', '${odontologo.nombre}','${odontologo.apellido}')">Modificar</button>
                <button class="btn btn-outline-danger btn-sm" onclick="deleteOdontologo(${odontologo.id})">Eliminar</button>
              </td>  
        </tr>              
            `;

        tableBody.appendChild(row);
      });
    })
    .catch((error) => {
      console.error("Error fetching data:", error);
    });
}

fetchOdontologos();
