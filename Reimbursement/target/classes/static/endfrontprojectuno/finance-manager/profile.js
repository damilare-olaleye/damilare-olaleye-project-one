window.addEventListener('load', async () => {

    let res = await fetch('http://localhost:8080/checkloginstatus', {
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
  
    populateProfileData();
    
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
  
  async function populateProfileData() {

    let res = await fetch('http://localhost:8080/userProfile',{
        credentials: 'include', 
        method: 'GET'

    });

    let tbodyElement = document.querySelector("#profile-table tbody");
    let profileData = await res.json();
    let tr = document.createElement('tr');

    let firstnameTd = document.createElement('td');
    firstnameTd.innerHTML = profileData.firstName;

    let lastnameTd = document.createElement('td')
    lastnameTd.innerHTML = profileData.lastName;

    let emailTd = document.createElement('td');
    emailTd.innerHTML = profileData.email;

    let roleTd = document.createElement('td');
    roleTd.innerHTML = profileData.role;

    tr.appendChild(firstnameTd);
    tr.appendChild(lastnameTd);
    tr.appendChild(emailTd);
    tr.appendChild(roleTd);

    tbodyElement.appendChild(tr);

  }