import React, { Component } from 'react';

import Form from 'react-bootstrap/Form';

import Button from 'react-bootstrap/Button';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faTrash, faSearch } from '@fortawesome/free-solid-svg-icons';
import InputGroup from 'react-bootstrap/InputGroup';
import Dropdown from 'react-bootstrap/Dropdown';
import DropdownButton from 'react-bootstrap/DropdownButton';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import Container from 'react-bootstrap/Container';
import ButtonGroup from 'react-bootstrap/ButtonGroup';
import NavBar from '../../components/NavBar';
import {
  cpfMask,
  telefoneMask,
  cepMask,
  celularMask,
  normalizeCep,
  normalizeCpf,
  normalizeTelefone,
} from '../../utils/masks';
import findCEP from '../../resources/via-cep.resource';
import UFS from '../../utils/ufs';
import { getAllTiposTelefones } from "../../resources/tipoTelefone.resource"
import {
  createTorcedor,
  getOne,
  updateTorcedor,
} from '../../resources/torcedor.resourse';
import Alert from 'react-bootstrap/Alert'

const TELEFONE_PADRAO = { numero: '', tipoTelefone: {id: null, descricao: '', celular: false}, principal: false };

export default class TorcedorForm extends Component {
  clienteId;

  isView = false;

  constructor(props) {
    super(props);
    const telefone = {...TELEFONE_PADRAO};
    telefone.principal = false;

    this.state = {
      erro: '',
      endereco: {cep: '', logradouro: '', bairro: '', localidade: '', uf: '', complemento: '', cepNotFound: false},
      nome: '',
      cpf: '',
      email: '',
      telefones: [telefone],
      tiposTelefones: []
    };
  }

  componentDidMount = () => {
    getAllTiposTelefones().then(tiposTelefones => {
      this.setState({tiposTelefones}) 
      // inicializa o primeiro caso não tenha telefone cadastrado ainda
      if(!this.state.telefones[0].id){
        const {telefones} = this.state;
        telefones[0].principal = true;
        telefones[0].tipoTelefone = tiposTelefones[0]
        this.setState({telefones})
      }
    } )
    // recuperar tipos de telefones
    // eslint-disable-next-line react/prop-types
    const { id } = this.props.match.params;

    if (this.props.match.path.includes('view')) {
      this.isView = true;
    }

    if (id) {
      this.initCliente(id);
    }
  };

  initCliente = id => {
    this.clienteId = id;
    getOne(id).then(res => {
      res.cpf = cpfMask(res.cpf);
      res.cep = cepMask(res.endereco.cep);
      res.telefones = res.telefones.map(item => {
        return {
          id: item.id,
          numero:
            item.tipoTelefone.celular ? celularMask(item.numero) : telefoneMask(item.numero),
            tipoTelefone: item.tipoTelefone,
            principal: item.principal
        };
      });
      this.setState({ ...res });
    });
  };

  buildNomeInput = () => {
    const { nome } = this.state;

    return (
      <Form.Group controlId="id-cliente_form_nome">
        <Form.Label>Nome</Form.Label>
        <Form.Control
          required
          onChange={e => this.handleGenericChange(e, 'nome')}
          minLength="3"
          maxLength="100"
          type="text"
          value={nome}
          placeholder="Nome do cliente"
        />
      </Form.Group>
    );
  };

  buildCPFInput = () => {
    const { cpf } = this.state;

    return (
      <Form.Group controlId="id-cliente_form_cpf">
        <Form.Label>CPF</Form.Label>
        <Form.Control
          required
          onChange={this.handleCPFtChange}
          value={cpf}
          type="text"
          placeholder="CPF do cliente"
        />
      </Form.Group>
    );
  };

  buildEmailInput = () => {
    const { email } = this.state;

    return (
      <Form.Group controlId="id-cliente_form_email">
        <Form.Label>E-mail</Form.Label>
        <InputGroup className="pb-2">
              <Form.Control
                required
                onChange={e => this.handleGenericChange(e, "email")}
                type="email"
                value={email}
                placeholder="E-mail do cliente"
              />
            </InputGroup>
      </Form.Group>
    );
  };

  buildTelefoneInput = () => {
    const { telefones, tiposTelefones } = this.state;

    return (
      <Form.Group controlId="id-cliente_form_telefone">
        <Form.Label>Telefone</Form.Label>
        {telefones.map((item, index) => {
          let tipoTelefoneSelecionada = item.tipoTelefone;
          if(!tipoTelefoneSelecionada.id && tiposTelefones.length > 0){
            tipoTelefoneSelecionada = tiposTelefones[0];
          }

          return (
            // eslint-disable-next-line react/no-array-index-key
            <InputGroup className="pb-5" key={index}>
              <InputGroup.Prepend>
                <DropdownButton id="id-cliente_form_telefone_tipo" variant="success" title={tipoTelefoneSelecionada.descricao}>
                  {tiposTelefones.map((tt, ttindex) => {
                    return (
                    <Dropdown.Item key={`${ttindex}-${index}`} onClick={() =>this.onTipoTelefoneSelected(tt, index)}> 
                      {tt.descricao}
                    </Dropdown.Item>)
                    })
                }
                </DropdownButton>
              </InputGroup.Prepend>
              <Form.Control
                required
                onChange={e => this.handleTelefoneChange(e, index)}
                type="text"
                maxLength={this.handleTelefoneMaxLength(item.tipoTelefone)}
                value={item.numero}
                placeholder="Telefone do cliente"
              />
              {this.buildRemoveButton(() => this.onRemovingTelefone(index))}

              <Form.Switch 
                id={`telprincipal-${index}`}
                label={item.principal ? "Principal" : "Alternativo"} 
                checked={item.principal} 
                onChange={e => this.handleTelefonePrincipalChange(e, index)} 
                />
            </InputGroup>
          );
        })}
        <Button onClick={this.onAddingMoreTelefones}>
          Adicionar outro telefone
        </Button>
      </Form.Group>
    );
  };

  buildRemoveButton = f => {
    return (
      <InputGroup.Append>
        <Button variant="danger" onClick={f}>
          <FontAwesomeIcon icon={faTrash} color="white" />
        </Button>
      </InputGroup.Append>
    );
  };

  buildCEPInput = () => {
    const { cep } = this.state.endereco;

    return (
      <Form.Group controlId="id-cliente_form_cep">
        <Form.Label>CEP</Form.Label>
        <InputGroup className="pb-2">
          <Form.Control
            required
            onChange={this.handleCEPChange}
            minLength="9"
            maxLength="9"
            type="text"
            placeholder="CEP"
            value={cep}
          />
          <InputGroup.Append>
            <Button onClick={this.onSearchingCEP}>
              <FontAwesomeIcon icon={faSearch} color="white" />
            </Button>
          </InputGroup.Append>
        </InputGroup>
        {this.state.cepNotFound ? (
          <small className="text-danger">CEP não encontrado</small>
        ) : null}
      </Form.Group>
    );
  };

  buildLogradouroInput = () => {
    const { logradouro } = this.state.endereco;

    return (
      <Form.Group controlId="id-cliente_form_logradouro">
        <Form.Label>Logradouro</Form.Label>
        <Form.Control
          required
          onChange={e => this.handleGenericChange(e, 'logradouro')}
          type="text"
          value={logradouro}
          placeholder="Logradouro"
        />
      </Form.Group>
    );
  };

  buildBairroInput = () => {
    const { bairro } = this.state.endereco;

    return (
      <Form.Group controlId="id-cliente_form_bairro">
        <Form.Label>Bairro</Form.Label>
        <Form.Control
          required
          onChange={e => this.handleGenericChange(e, 'bairro')}
          type="text"
          value={bairro}
          placeholder="Bairro"
        />
      </Form.Group>
    );
  };

  buildLocalidadeInput = () => {
    const { localidade } = this.state.endereco;

    return (
      <Form.Group controlId="id-cliente_form_localidade">
        <Form.Label>Cidade</Form.Label>
        <Form.Control
          required
          onChange={e => this.handleGenericChange(e, 'localidade')}
          type="text"
          value={localidade}
          placeholder="Cidade"
        />
      </Form.Group>
    );
  };

  buildUFInput = () => {
    const { uf } = this.state.endereco;

    return (
      <Form.Group controlId="id-cliente_form_uf">
        <Form.Label>UF</Form.Label>
        <DropdownButton id="id-cliente_form_uf_dropdown" title={uf}>
          {UFS.map(item => {
            return (
              <Dropdown.Item
                key={item.ID}
                onClick={() => this.onUFSelection(item.Sigla)}
              >
                {item.Sigla}
              </Dropdown.Item>
            );
          })}
        </DropdownButton>
      </Form.Group>
    );
  };

  buildComplementoInput = () => {
    const { complemento } = this.state.endereco;

    return (
      <Form.Group controlId="id-cliente_form_complemento">
        <Form.Label>Complemento</Form.Label>
        <Form.Control
          onChange={e => this.handleGenericChange(e, 'complemento')}
          type="text"
          value={complemento}
          placeholder="Complemento"
        />
      </Form.Group>
    );
  };

  buildSubmitButton = () => {
    return <Button type="submit">Salvar</Button>;
  };

  buildCancelarButton = () => {
    return (
      <Button onClick={this.handleCancelar} variant="danger" type="button">
        Cancelar
      </Button>
    );
  };

  handleCancelar = () => {
    // eslint-disable-next-line react/prop-types
    const { history } = this.props;

    // eslint-disable-next-line react/prop-types
    history.push('/home');
  };

  handleEmailChange = (event, index) => {
    const { emails } = this.state;

    emails[index].email = event.target.value;

    this.setState({ emails });
  };

  handleGenericChange = (event, key) => {
    this.setState({ [key]: event.target.value });
  };

  handleCEPChange = e =>  {
    const endereco = {...this.state.endereco};
    endereco.cep = cepMask(e.target.value)
    this.setState({endereco});
  };

  handleCPFtChange = e => {
    this.setState({ cpf: cpfMask(e.target.value) });
  };

  handleTelefonePrincipalChange = (event, index) => {
    if(event.target.checked){
      const { telefones } = this.state;

      telefones.forEach(tel => tel.principal = false);
      telefones[index].principal = true;

      this.setState({ telefones })
    }
  };

  handleTelefoneChange = (event, index) => {
    const { telefones } = this.state;

    if (telefones[index].tipoTelefone.celular) {
      telefones[index].numero = celularMask(event.target.value);
    } else {
      telefones[index].numero = telefoneMask(event.target.value);
    }

    this.setState({ telefones });
  };

  handleTelefoneMaxLength = tipo => {
    if (tipo.celular) return 15;
    return 14;
  };

  onAddingMoreTelefones = () => {
    const { telefones } = this.state;

    const tel = {...TELEFONE_PADRAO};

    this.setState({
      telefones: [...telefones, tel],
    });
  };

  onRemovingTelefone = index => {
    const { telefones } = this.state;
    if (telefones.length === 1) return;

    telefones.splice(index, 1);
    this.setState({ telefones });
  };

  onUFSelection = uf => {
    const endereco = {...this.state.endereco};
    endereco.uf = uf;
    this.setState({ endereco });
  };

  onSearchingCEP = () => {
    const { cep } = this.state.endereco;
    const cepString = normalizeCep(cep);
    if (cepString.length !== 8) return;

    findCEP(cep).then(this.onSuccessfulCEPSearch);
  };

  onSuccessfulCEPSearch = cep => {
    if (cep.hasOwnProperty('erro')) {
      this.setState({ cepNotFound: true });
      return;
    }
    this.setState({ cepNotFound: false });

    Object.keys(cep).forEach(key => {
      const endereco = {...this.state.endereco};
      endereco[key] = cep[key];
      this.setState({ endereco });
    });
  };

  onTipoTelefoneSelected = (tipo, index) => {
    const { telefones } = this.state;
    telefones[index].tipoTelefone = tipo;
    this.setState({ telefones: [...telefones] });
  };

  onSubmission = e => {
    e.preventDefault();
    const userToSave = { ...this.state };
    const { cpf } = userToSave;
    const { cep } = userToSave.endereco;
    // eslint-disable-next-line react/prop-types
    const { history } = this.props;

    userToSave.cpf = normalizeCpf(cpf);
    userToSave.endereco.cep = normalizeCep(cep);
    userToSave.telefones.forEach(item => {
      // eslint-disable-next-line no-param-reassign
      item.numero = normalizeTelefone(item.numero);
    });

    if (this.clienteId) {
      // eslint-disable-next-line react/prop-types
      updateTorcedor(userToSave).then(() => history.push('/home'));
    } else {
      // eslint-disable-next-line react/prop-types
      createTorcedor(userToSave)
          .then(() => history.push('/home'))
          .catch(err => {
            this.setState({erro: err.response.data.mensagem})
          });
    }
  };

  render() {
    return (
      <>
        <NavBar props={this.props} />
        <fieldset disabled={this.isView}>
          <Form onSubmit={this.onSubmission}>
            <Container className="container-fluid pt-3 pb-4">
              <Alert variant="danger" show={this.state.erro} onClose={() => this.setState({erro: ""})} dismissible>
                <Alert.Heading>Ops, temos um erro!</Alert.Heading>
                <p>
                  { this.state.erro }
                </p>
              </Alert>
              <Row className="justify-content-center">
                <Col md="6" col="12">
                  <legend>Dados do Cliente</legend>
                  {this.buildNomeInput()}
                  {this.buildCPFInput()}
                </Col>
                <Col md="6" col="12">
                  <legend>Dados de Contato</legend>
                  {this.buildEmailInput()}
                  {this.buildTelefoneInput()}
                </Col>
                <Col md="8" col="12">
                  <legend>Dados de Endereço</legend>
                  {this.buildCEPInput()}
                  {this.buildLogradouroInput()}
                  {this.buildComplementoInput()}
                  {this.buildBairroInput()}
                  {this.buildLocalidadeInput()}
                  {this.buildUFInput()}
                </Col>
                <Col md="8" className="flex-row-reverse d-flex">
                  <ButtonGroup>
                    {this.buildCancelarButton()}
                    {this.buildSubmitButton()}
                  </ButtonGroup>
                </Col>
              </Row>
            </Container>
          </Form>
        </fieldset>
      </>
    );
  }
}
