window.addEventListener('load', async () => {

    let res = await fetch('http://ec2-3-138-126-45.us-east-2.compute.amazonaws.com:8081/checkloginstatus', {
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
  
    getAndPopulateUserByNames()

  });
  
  let logoutBtn = document.querySelector('#logout');
  
  logoutBtn.addEventListener('click', async() => {
  
    let res = await fetch('http://ec2-3-138-126-45.us-east-2.compute.amazonaws.com:8081/logout', {
      method: 'POST', 
      credentials: 'include'
    });
    
    if (res.status === 200){
      window.location.href = '../index.html';
    } else if(res.status === 400 || res.status == 404){
      window.location.href = '../../404/404.html';
  
    }
  
  });


let findNamesButton = document.querySelector('#findnames');
findNamesButton.addEventListener('click', getAndPopulateUserByNames);

async function getAndPopulateUserByNames() {

  const searchInput = document.querySelector('#findAllNames');

    let res = await fetch('http://ec2-3-138-126-45.us-east-2.compute.amazonaws.com:8081/searchUsers', {
      method: 'POST',
      credentials: 'include',
      body:JSON.stringify({
        firstName: searchInput.value
      })
    });

    console.log(res);

    if(res.status === 200 || res.status === 201){
      let usernameTable = await res.json();
      let tbodyElement = document.querySelector("#names-table");
  
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

    } else if(res.status === 400 || res.status === 404){
    
      let submitSearchErrMsg = document.createElement('p');
      let SubmitSearchDiv = document.querySelector('#submit-search-err');
  
      SubmitSearchDiv.innerHTML = '';
      SubmitSearchDiv.innerHTML = data;
      submitSearchErrMsg.style.color = 'red';

    }  else {
        window.location.href = '/404/404.html';
      }
}