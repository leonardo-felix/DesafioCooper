import API from '../config/aixos.config';

const RESOURCE_URL = '/torcedor';

export const getAllTorcedores = () => {
  return API.get(RESOURCE_URL).then(res => res.data);
};

export const createTorcedor = torcedor => {
  return API.post(RESOURCE_URL, torcedor).then(res => res.data);
};

export const updateTorcedor = torcedor => {
  return API.put(RESOURCE_URL, torcedor).then(res => res.data);
};

export const deleteTorcedor = torcedorId => {
  return API.delete(`${RESOURCE_URL}/${torcedorId}`);
};

export const getOne = torcedorId => {
  return API.get(`${RESOURCE_URL}/${torcedorId}`).then(res => res.data);
};
