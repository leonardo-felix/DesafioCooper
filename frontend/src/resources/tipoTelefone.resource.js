import API from '../config/aixos.config';

const RESOURCE_URL = '/tipoTelefone';

export const getAllTiposTelefones = () => {
  return API.get(RESOURCE_URL).then(res => res.data);
};
