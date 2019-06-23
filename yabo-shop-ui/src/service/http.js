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
  }

};
