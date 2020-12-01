import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IQuestion } from 'app/shared/model/question.model';
import { getEntities as getQuestions } from 'app/entities/question/question.reducer';
import { IServiceUser } from 'app/shared/model/service-user.model';
import { getEntities as getServiceUsers } from 'app/entities/service-user/service-user.reducer';
import { IEmployee } from 'app/shared/model/employee.model';
import { getEntities as getEmployees } from 'app/entities/employee/employee.reducer';
import { getEntity, updateEntity, createEntity, reset } from './answer.reducer';
import { IAnswer } from 'app/shared/model/answer.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IAnswerUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const AnswerUpdate = (props: IAnswerUpdateProps) => {
  const [questionId, setQuestionId] = useState('0');
  const [serviceUserId, setServiceUserId] = useState('0');
  const [recordedById, setRecordedById] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { answerEntity, questions, serviceUsers, employees, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/answer' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getQuestions();
    props.getServiceUsers();
    props.getEmployees();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.createdDate = convertDateTimeToServer(values.createdDate);
    values.lastUpdatedDate = convertDateTimeToServer(values.lastUpdatedDate);

    if (errors.length === 0) {
      const entity = {
        ...answerEntity,
        ...values,
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="carePlannerApp.answer.home.createOrEditLabel">
            <Translate contentKey="carePlannerApp.answer.home.createOrEditLabel">Create or edit a Answer</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : answerEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="answer-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="answer-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="answerLabel" for="answer-answer">
                  <Translate contentKey="carePlannerApp.answer.answer">Answer</Translate>
                </Label>
                <AvField id="answer-answer" type="text" name="answer" />
              </AvGroup>
              <AvGroup>
                <Label id="descriptionLabel" for="answer-description">
                  <Translate contentKey="carePlannerApp.answer.description">Description</Translate>
                </Label>
                <AvField id="answer-description" type="text" name="description" />
              </AvGroup>
              <AvGroup>
                <Label id="attribute1Label" for="answer-attribute1">
                  <Translate contentKey="carePlannerApp.answer.attribute1">Attribute 1</Translate>
                </Label>
                <AvField id="answer-attribute1" type="text" name="attribute1" />
              </AvGroup>
              <AvGroup>
                <Label id="attribute2Label" for="answer-attribute2">
                  <Translate contentKey="carePlannerApp.answer.attribute2">Attribute 2</Translate>
                </Label>
                <AvField id="answer-attribute2" type="text" name="attribute2" />
              </AvGroup>
              <AvGroup>
                <Label id="attribute3Label" for="answer-attribute3">
                  <Translate contentKey="carePlannerApp.answer.attribute3">Attribute 3</Translate>
                </Label>
                <AvField id="answer-attribute3" type="text" name="attribute3" />
              </AvGroup>
              <AvGroup>
                <Label id="attribute4Label" for="answer-attribute4">
                  <Translate contentKey="carePlannerApp.answer.attribute4">Attribute 4</Translate>
                </Label>
                <AvField id="answer-attribute4" type="text" name="attribute4" />
              </AvGroup>
              <AvGroup>
                <Label id="attribute5Label" for="answer-attribute5">
                  <Translate contentKey="carePlannerApp.answer.attribute5">Attribute 5</Translate>
                </Label>
                <AvField id="answer-attribute5" type="text" name="attribute5" />
              </AvGroup>
              <AvGroup>
                <Label id="createdDateLabel" for="answer-createdDate">
                  <Translate contentKey="carePlannerApp.answer.createdDate">Created Date</Translate>
                </Label>
                <AvInput
                  id="answer-createdDate"
                  type="datetime-local"
                  className="form-control"
                  name="createdDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.answerEntity.createdDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="lastUpdatedDateLabel" for="answer-lastUpdatedDate">
                  <Translate contentKey="carePlannerApp.answer.lastUpdatedDate">Last Updated Date</Translate>
                </Label>
                <AvInput
                  id="answer-lastUpdatedDate"
                  type="datetime-local"
                  className="form-control"
                  name="lastUpdatedDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.answerEntity.lastUpdatedDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="clientIdLabel" for="answer-clientId">
                  <Translate contentKey="carePlannerApp.answer.clientId">Client Id</Translate>
                </Label>
                <AvField
                  id="answer-clientId"
                  type="string"
                  className="form-control"
                  name="clientId"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label for="answer-question">
                  <Translate contentKey="carePlannerApp.answer.question">Question</Translate>
                </Label>
                <AvInput id="answer-question" type="select" className="form-control" name="questionId">
                  <option value="" key="0" />
                  {questions
                    ? questions.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.question}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="answer-serviceUser">
                  <Translate contentKey="carePlannerApp.answer.serviceUser">Service User</Translate>
                </Label>
                <AvInput id="answer-serviceUser" type="select" className="form-control" name="serviceUserId">
                  <option value="" key="0" />
                  {serviceUsers
                    ? serviceUsers.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.serviceUserCode}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="answer-recordedBy">
                  <Translate contentKey="carePlannerApp.answer.recordedBy">Recorded By</Translate>
                </Label>
                <AvInput id="answer-recordedBy" type="select" className="form-control" name="recordedById">
                  <option value="" key="0" />
                  {employees
                    ? employees.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.employeeCode}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/answer" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  questions: storeState.question.entities,
  serviceUsers: storeState.serviceUser.entities,
  employees: storeState.employee.entities,
  answerEntity: storeState.answer.entity,
  loading: storeState.answer.loading,
  updating: storeState.answer.updating,
  updateSuccess: storeState.answer.updateSuccess,
});

const mapDispatchToProps = {
  getQuestions,
  getServiceUsers,
  getEmployees,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(AnswerUpdate);
