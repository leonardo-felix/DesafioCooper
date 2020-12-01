import React, { Component } from 'react';

import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import Col from 'react-bootstrap/Col';
import Row from 'react-bootstrap/Row';

import Container from 'react-bootstrap/Container';
import singIn from '../../resources/login.resource';
import { saveUser } from '../../services/auth.service';
import Alert from "react-bootstrap/Alert";

export default class Login extends Component {
  constructor(props) {
    super(props);
    this.state = {
      login: '',
      senha: '',
      error: '',
    };
  }

  handleInputChanges = (event, field) => {
    this.setState({ [field]: event.target.value });
  };

  onSubmit = async e => {
    e.preventDefault();
    try {
      singIn(this.state).then(res => {
        saveUser(res.headers.authorization);
        const { history } = this.props;
        history.push('/home');
      })
          .catch(err => {
            if(err.response.status === 403){
              this.setState({error: "Usuário ou senha inválidos"})
            }
          });
    } catch (err) {
      console.error('Error ao realizar login: ', err);
    }
  };

  render() {
    return (
      <div>
        <Form onSubmit={this.onSubmit}>
          <Container className="container-fluid">
            <Row className="justify-content-md-center align-items-center vh-100">
              <Col md="12" lg="4">
                <Alert variant="danger" show={this.state.error} onClose={() => this.setState({error: ""})} dismissible>
                  <Alert.Heading>Ops, temos um erro!</Alert.Heading>
                  <p>
                    { this.state.error }
                  </p>
                </Alert>
                <Form.Group controlId="id-login_form">
                  <Form.Label>Login</Form.Label>
                  <Form.Control
                    required
                    type="text"
                    placeholder="Digite sue login"
                    onChange={event => this.handleInputChanges(event, 'login')}
                  />
                </Form.Group>

                <Form.Group controlId="id-password_form">
                  <Form.Label>Senha</Form.Label>
                  <Form.Control
                    required
                    type="password"
                    placeholder="Digite sua senha"
                    onChange={event =>
                      this.handleInputChanges(event, 'senha')
                    }
                  />
                </Form.Group>

                <Button variant="primary" type="submit" block>
                  Entrar
                </Button>
              </Col>
            </Row>
          </Container>
        </Form>
      </div>
    );
  }
}
