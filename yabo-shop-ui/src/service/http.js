import axios from "axios";
let baseUrl = '/api';

export default {
  get: (url, params = {}, headers) => {
    return axios({
      url: `${baseUrl}/${url}`,
      method: "get",
      params,
      headers
    });
  },

  post: (url, data, headers) => {
    return axios({
      url: `${baseUrl}/${url}`,
      method: "post",
      data,
      headers
    });
  },

  put: (url, data, headers) => {
    return axios({
      url: `${baseUrl}/${url}`,
      method: "put",
      data,
      headers
    });
  },

  patch: (url, data, headers) => {
    return axios({
      url: `${baseUrl}/${url}`,
      method: "patch",
      data,
      headers
    });
  },

  delete: url => {
    return axios({
      url: `${baseUrl}/${url}`,
      method: "delete"
    });
  },

  download: (url, params, successCallback, errorCallback) => {
    axios({
      url,
      method: "get",
      params,
      responseType: "blob"
    })
      .then(response => {
        let disposition = response.headers["content-disposition"];
        let filename = decodeURI(
          disposition.match(/filename[^;=\n]*=((['"]).*?\2|[^;\n]*)/)[1]
        );
        const url = window.URL.createObjectURL(new Blob([response.data]));
        const link = document.createElement("a");
        link.href = url;
        link.setAttribute("download", filename); //or any other extension
        document.body.appendChild(link);
        link.click();
        link.parentNode.removeChild(link);
        if (typeof successCallback === "function") successCallback(response);
      })
      .catch(error => {
        if (typeof errorCallback === "function") errorCallback(response);
      });
  }
};
