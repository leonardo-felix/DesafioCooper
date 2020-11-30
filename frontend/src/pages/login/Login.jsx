import React, { Component } from 'react';

import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import Col from 'react-bootstrap/Col';
import Row from 'react-bootstrap/Row';

import Container from 'react-bootstrap/Container';
import singIn from '../../resources/login.resource';
import { saveUser } from '../../services/auth.service';

export default class Login extends Component {
  constructor(props) {
    super(props);
    this.state = {
      login: '',
      senha: '',
      error: null,
    };
  }

  handleInputChanges = (event, field) => {
    this.setState({ [field]: event.target.value });
  };

  onSubmit = async e => {
    e.preventDefault();
    try {
      const res = await singIn(this.state);
      saveUser(res.headers.authorization);
      const { history } = this.props;
      history.push('/home');
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
