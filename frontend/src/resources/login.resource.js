import API from '../config/aixos.config';

const singIn = ({ login, senha }) => {
  return API.post('/login', { login, senha });
};

export default singIn;
