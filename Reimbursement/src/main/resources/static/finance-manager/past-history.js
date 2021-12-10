window.addEventListener('load', async () => {

    let res = await fetch('http://ec2-3-138-126-45.us-east-2.compute.amazonaws.com:8080/checkloginstatus', {
        credentials: 'include',
        method: 'GET'
    });
  
    if (res.status === 200) {
        let userObj = await res.json();
  
        if (userObj.role === 'Employee') {
            window.location.href = '/employee/home.html';
        }
    } else if (res.status === 401 || res.status === 404)  {
        window.location.href = '/404/404.html';
    }
  
    getAndPopulateAllReimbursements();

  });

  let logoutBtn = document.querySelector('#logout');

  logoutBtn.addEventListener('click', async() => {

    let res = await fetch('http://ec2-3-138-126-45.us-east-2.compute.amazonaws.com:8080/logout', {
      method: 'POST', 
      credentials: 'include'
    });
    
    if (res.status === 200 || res.status === 201){
      window.location.href = '../index.html';
    } else if(res.status === 400 || res.status == 404){
      window.location.href = '../../404/404.html';
  
    }
  
  });

  async function getAndPopulateAllReimbursements() {
  
    let res = await fetch('http://ec2-3-138-126-45.us-east-2.compute.amazonaws.com:8080/allreimbursements',{
      method: 'GET',
      credentials: 'include'
     
  });

  console.log(res);
  
      let allReimbsArray = await res.json();
      let tbodyElement = document.querySelector("#all-reimbs-table tbody");
  
      console.log(allReimbsArray);
      console.log(tbodyElement);
  
      for (let i = 0; i < allReimbsArray.length; i++) {
  
          let employeeReimbsArray = allReimbsArray[i];
  
          let tr = document.createElement('tr');
  
          let tableColOne = document.createElement('td');
          tableColOne.innerHTML = employeeReimbsArray.reimbId;
  
          let tableColTwo = document.createElement('td');
          tableColTwo.innerHTML = employeeReimbsArray.submitted;
  
          let tableColThree = document.createElement('td');
          tableColThree.innerHTML = employeeReimbsArray.resolved;
  
          let tableColFour = document.createElement('td');
          tableColFour.innerHTML = employeeReimbsArray.status;
  
          let tableColFive = document.createElement('td');
          tableColFive.innerHTML = employeeReimbsArray.type;
  
          let tableColSix = document.createElement('td');
          tableColSix.innerHTML = employeeReimbsArray.description;
  
          let tableColSeven = document.createElement('td');
          tableColSeven.innerHTML = employeeReimbsArray.amount;
  
          let tableColEight = document.createElement('td');
          tableColEight.innerHTML = employeeReimbsArray.author;
  
          let tableColNine = document.createElement('td');
          tableColNine.innerHTML = employeeReimbsArray.resolver;

          // let tableColTen = document.createElement('td');
          // tableColTen.innerHTML = document.createElement.username;
  
          tr.appendChild(tableColOne);
          tr.appendChild(tableColTwo);
          tr.appendChild(tableColThree);
          tr.appendChild(tableColFour);
          tr.appendChild(tableColFive);
          tr.appendChild(tableColSix);
          tr.appendChild(tableColSeven);
          tr.appendChild(tableColEight);
          tr.appendChild(tableColNine);
          // tr.appendChild(tableColTen);
  
          tbodyElement.appendChild(tr);
  
      }


    if(res.status === 400 || res.status == 404){
      
      let statusErrorMessage = document.createElement('p');
      let statusDiv = document.querySelector('#err-msg');

       statusDiv.innerHTML = '';
       statusErrorMessage.innerHTML = statusArray.message;
       statusErrorMessage.style.color = 'red';
       statusErrorMessage.style.fontSize = '22px';
       statusDiv.appendChild(statusErrorMessage);

    } else if (res.status === 500){
      window.location.href = '../../404/404.html';
  }
  
  
  }