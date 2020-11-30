import React, { Component } from 'react';

import Table from 'react-bootstrap/Table';
import Button from 'react-bootstrap/Button';
import ButtonGroup from 'react-bootstrap/ButtonGroup';

import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import Modal from 'react-bootstrap/Modal';
import { cepMask, cpfMask } from '../../utils/masks';
import {
  deleteTorcedor,
  getAllTorcedores,
} from '../../resources/torcedor.resourse';
import NavBar from '../../components/NavBar';
import { isUserAdmin } from '../../services/auth.service';

class Home extends Component {
  torcedorToRemove;

  isAdmin;

  constructor(props) {
    super(props);
    this.state = {
      torcedors: [],
      showModal: false,
    };
    this.isAdmin = isUserAdmin();
  }

  componentDidMount() {
    this.findAll();
  }

  findAll = () => {
    getAllTorcedores().then(res => {
      this.setState({ torcedors: res });
    });
  };

  handleShow = torcedorId => {
    const { showModal } = this.state;
    if (!showModal || typeof torcedorId === 'number') {
      this.torcedorToRemove = torcedorId;
    }
    this.setState({ showModal: !showModal });
  };

  modal = () => {
    const { showModal } = this.state;

    return (
      <Modal show={showModal} onHide={this.handleShow}>
        <Modal.Header closeButton>
          <Modal.Title>Remover Torcedor</Modal.Title>
        </Modal.Header>
        <Modal.Body>Tem certeza que deja remover esse Torcedor?</Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={this.handleShow}>
            cancelar
          </Button>
          <Button variant="primary" onClick={this.onRemove}>
            Remover
          </Button>
        </Modal.Footer>
      </Modal>
    );
  };

  onNavigation = (id, action) => {
    const { history } = this.props;

    history.push(`/torcedor/${action}/${id}`);
  };

  onRemove = () => {
    if (this.torcedorToRemove) {
      deleteTorcedor(this.torcedorToRemove).then(this.findAll);
      this.handleShow(null);
    }
  };

  render() {
    const { torcedors } = this.state;

    return (
      <>
        {this.modal()}
        <NavBar />
        <h1 align="center" className="p-4">
          Torcedores
        </h1>

        <Row className="justify-content-md-center align-items-center">
          <Col md="12" lg="9">
            <Table striped bordered hover>
              <thead>
                <tr>
                  <th>Nome</th>
                  <th>CPF</th>
                  <th>CEP</th>
                  <th>Cidade</th>
                  <th>Ações</th>
                </tr>
              </thead>
              <tbody>
                {torcedors.map(torcedor => {
                  return (
                    <tr key={torcedor.id}>
                      <td>{torcedor.nome}</td>
                      <td>{cpfMask(torcedor.cpf)}</td>
                      <td>{cepMask(torcedor.endereco.cep)}</td>
                      <td>{torcedor.endereco.localidade}</td>
                      <td width="10">
                        <ButtonGroup>
                          {isUserAdmin() ? (
                            <Button
                              onClick={() =>
                                this.onNavigation(torcedor.id, 'edit')
                              }
                            >
                              Editar
                            </Button>
                          ) : null}
                          <Button
                            onClick={() =>
                              this.onNavigation(torcedor.id, 'view')
                            }
                            variant="info"
                          >
                            Visualizar
                          </Button>
                          {isUserAdmin() ? (
                            <Button
                              variant="danger"
                              onClick={() => this.handleShow(torcedor.id)}
                            >
                              Excluir
                            </Button>
                          ) : null}
                        </ButtonGroup>
                      </td>
                    </tr>
                  );
                })}
              </tbody>
            </Table>
          </Col>
        </Row>
      </>
    );
  }
}

export default Home;
