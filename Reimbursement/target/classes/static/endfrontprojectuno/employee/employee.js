window.addEventListener('load', async () => {

  let res = await fetch('http://localhost:8080/checkloginstatus', {
      credentials: 'include',
      method: 'GET'
  });

  if (res.status === 200) {
      let userObj = await res.json();

      if (userObj.role === 'Finance Manager') {
          window.location.href = 'finance-manager.html';
      }
  } else if (res.status === 401 || res.status === 404)  {
      window.location.href = '/404/404.html';
  }

});

let logoutBtn = document.querySelector('#logout');

logoutBtn.addEventListener('click', async() => {

  let res = await fetch('http://localhost:8080/logout', {
    method: 'POST', 
    credentials: 'include'
  });
  
  if (res.status === 200){
    window.location.href = '../../authentication/index.html';
  } else if(res.status === 400 || res.status == 404){
    window.location.href = '../../404/404.html';

  }

});

// submit new reibursement request
let submitNewReibursementButton = document.querySelector('#submit-request-btn');

submitNewReibursementButton.addEventListener('click', async() => {

  let reimbursementTypeInput = document.querySelector('#reimbType');
  let reimbursementAmountInput = document.querySelector('#reimbAmount');
  let reimbursementDescriptionInput = document.querySelector('#reimbDescrip');
  let reimbursementImageInput = document.querySelector('#receipt-file');

  let recieptFile = reimbursementImageInput.files[0];

  let formData = new FormData();
  formData.append('type', reimbursementTypeInput.value);
  formData.append('amount', reimbursementAmountInput.value);
  formData.append('description', reimbursementDescriptionInput.value);
  formData.append('receipt', recieptFile);

  let res = await fetch('http://localhost:8080/submitRequest', {
    method: 'POST', 
    credentials: 'include',
    body: formData
  });


  let data = await res.text();

  if(res.status === 201 || res.status === 200) {

    let successSubmittedMessage = document.createElement('p');
    let submitDiv = document.querySelector('#submit-info');

    successSubmittedMessage.innerHTML = data;
    successSubmittedMessage.style.color = 'green';
    submitDiv.appendChild(successSubmittedMessage);

    setTimeout(() => window.location.reload(), 1000);

  } else if (res.status === 400 || res.status === 404){
    window.location.href = '/404/404.html';
  }

});

let findUsernameButton = document.querySelector('#findUsername');
findUsernameButton.addEventListener('click', getAndPopulateUserByUsername);

async function getAndPopulateUserByUsername() {

  const searchInput = document.querySelector('#searchUsername');

    let res = await fetch('http://localhost:8080/userByUsername', {
      method: 'POST',
      credentials: 'include',
      body:JSON.stringify({
        username: searchInput.value
      })
    });

    console.log(res);

    if(res.status === 200 || res.status === 201){
      let usernameTable = await res.json();
      let tbodyElement = document.querySelector("#username-table");
  
      console.log(usernameTable);
      console.log(tbodyElement);
  
      let tr = document.createElement('tr');
  
      let tableColOne = document.createElement('td');
      tableColOne.innerHTML = usernameTable.firstName;
  
      let tableColTwo = document.createElement('td');
      tableColTwo.innerHTML = usernameTable.lastName;
  
      let tableColThree = document.createElement('td');
      tableColThree.innerHTML = usernameTable.email;
  
      let tableColFour = document.createElement('td');
      tableColFour.innerHTML = usernameTable.role;
  
      tr.appendChild(tableColOne);
      tr.appendChild(tableColTwo);
      tr.appendChild(tableColThree);
      tr.appendChild(tableColFour);
  
      tbodyElement.appendChild(tr);
  
      setTimeout(() => window.location.reload(), 7000);

    } else if(res.status === 400 || res.status === 404){
      window.location.href = '../../404/404.html';

    } 
}

// Get the Sidebar
var mySidebar = document.getElementById("mySidebar");

// Get the DIV with overlay effect
var overlayBg = document.getElementById("myOverlay");

// Toggle between showing and hiding the sidebar, and add overlay effect
function w3_open() {
  if (mySidebar.style.display === 'block') {
    mySidebar.style.display = 'none';
    overlayBg.style.display = "none";
  } else {
    mySidebar.style.display = 'block';
    overlayBg.style.display = "block";
  }
}

// Close the sidebar with the close button
function w3_close() {
  mySidebar.style.display = "none";
  overlayBg.style.display = "none";
}

