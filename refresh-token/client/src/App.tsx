import {
  Accordion,
  Button,
  Col,
  Container,
  FloatingLabel,
  Form,
  InputGroup,
  Row,
  Stack,
} from 'react-bootstrap';

function App() {
  return (
    <Container>
      <h2 className="my-4 text-center">My Token Client</h2>
      <Row>
        <Col>
          <Accordion defaultActiveKey={'0'} alwaysOpen>
            <Accordion.Item eventKey="0">
              <Accordion.Header>이메일 / 비밀번호 입력</Accordion.Header>
              <Accordion.Body>
                <FloatingLabel
                  controlId="floatingInput"
                  label="Email"
                  className="mb-3"
                >
                  <Form.Control type="email" placeholder="name@example.com" />
                </FloatingLabel>
                <FloatingLabel controlId="floatingPassword" label="Password">
                  <Form.Control type="password" placeholder="Password" />
                </FloatingLabel>

                <div className="d-grid gap-2">
                  <Button variant="primary" size="sm">
                    Block level button
                  </Button>
                </div>
              </Accordion.Body>
            </Accordion.Item>

            <Accordion.Item eventKey="1">
              <Accordion.Header>토큰 설정 및 발급</Accordion.Header>
              <Accordion.Body>
                <Stack direction="horizontal" gap={2}>
                  <InputGroup size="sm">
                    <Form.Control
                      type="number"
                      min="0"
                      max="365"
                      value={1}
                      // onChange={(e) => setDays(Number(e.target.value))}
                    />
                    <InputGroup.Text>일</InputGroup.Text>
                  </InputGroup>

                  <InputGroup size="sm">
                    <Form.Control
                      type="number"
                      min="0"
                      max="23"
                      value={10}
                      // onChange={(e) => setHours(Number(e.target.value))}
                    />
                    <InputGroup.Text>시간</InputGroup.Text>
                  </InputGroup>

                  <InputGroup size="sm">
                    <Form.Control
                      type="number"
                      min="0"
                      max="59"
                      value={10}
                      // onChange={(e) => setMinutes(Number(e.target.value))}
                    />
                    <InputGroup.Text>분</InputGroup.Text>
                  </InputGroup>
                </Stack>
              </Accordion.Body>
            </Accordion.Item>

            <Accordion.Item eventKey="2">
              <Accordion.Header>API 호출</Accordion.Header>
              <Accordion.Body>입력</Accordion.Body>
            </Accordion.Item>
          </Accordion>
        </Col>
        <Col>
          <Form.Control
            as="textarea"
            id="logArea"
            readOnly
            style={{
              backgroundColor: '#f8f9fa',
              height: '80vh',
              resize: 'none',
              overflow: 'auto',
            }}
          />
          <div className="d-grid gap-2">
            <Button variant="primary" size="lg">
              Clear
            </Button>
          </div>
        </Col>
      </Row>
    </Container>
  );
}

export default App;
