import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './question.reducer';
import { IQuestion } from 'app/shared/model/question.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IQuestionUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const QuestionUpdate = (props: IQuestionUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { questionEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/question' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }
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
        ...questionEntity,
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
          <h2 id="carePlannerApp.question.home.createOrEditLabel">
            <Translate contentKey="carePlannerApp.question.home.createOrEditLabel">Create or edit a Question</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : questionEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="question-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="question-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="questionLabel" for="question-question">
                  <Translate contentKey="carePlannerApp.question.question">Question</Translate>
                </Label>
                <AvField id="question-question" type="text" name="question" />
              </AvGroup>
              <AvGroup>
                <Label id="descriptionLabel" for="question-description">
                  <Translate contentKey="carePlannerApp.question.description">Description</Translate>
                </Label>
                <AvField id="question-description" type="text" name="description" />
              </AvGroup>
              <AvGroup>
                <Label id="attribute1Label" for="question-attribute1">
                  <Translate contentKey="carePlannerApp.question.attribute1">Attribute 1</Translate>
                </Label>
                <AvField id="question-attribute1" type="text" name="attribute1" />
              </AvGroup>
              <AvGroup>
                <Label id="attribute2Label" for="question-attribute2">
                  <Translate contentKey="carePlannerApp.question.attribute2">Attribute 2</Translate>
                </Label>
                <AvField id="question-attribute2" type="text" name="attribute2" />
              </AvGroup>
              <AvGroup>
                <Label id="attribute3Label" for="question-attribute3">
                  <Translate contentKey="carePlannerApp.question.attribute3">Attribute 3</Translate>
                </Label>
                <AvField id="question-attribute3" type="text" name="attribute3" />
              </AvGroup>
              <AvGroup>
                <Label id="attribute4Label" for="question-attribute4">
                  <Translate contentKey="carePlannerApp.question.attribute4">Attribute 4</Translate>
                </Label>
                <AvField id="question-attribute4" type="text" name="attribute4" />
              </AvGroup>
              <AvGroup>
                <Label id="attribute5Label" for="question-attribute5">
                  <Translate contentKey="carePlannerApp.question.attribute5">Attribute 5</Translate>
                </Label>
                <AvField id="question-attribute5" type="text" name="attribute5" />
              </AvGroup>
              <AvGroup check>
                <Label id="optionalLabel">
                  <AvInput id="question-optional" type="checkbox" className="form-check-input" name="optional" />
                  <Translate contentKey="carePlannerApp.question.optional">Optional</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label id="createdDateLabel" for="question-createdDate">
                  <Translate contentKey="carePlannerApp.question.createdDate">Created Date</Translate>
                </Label>
                <AvInput
                  id="question-createdDate"
                  type="datetime-local"
                  className="form-control"
                  name="createdDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.questionEntity.createdDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="lastUpdatedDateLabel" for="question-lastUpdatedDate">
                  <Translate contentKey="carePlannerApp.question.lastUpdatedDate">Last Updated Date</Translate>
                </Label>
                <AvInput
                  id="question-lastUpdatedDate"
                  type="datetime-local"
                  className="form-control"
                  name="lastUpdatedDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.questionEntity.lastUpdatedDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="clientIdLabel" for="question-clientId">
                  <Translate contentKey="carePlannerApp.question.clientId">Client Id</Translate>
                </Label>
                <AvField
                  id="question-clientId"
                  type="string"
                  className="form-control"
                  name="clientId"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup check>
                <Label id="hasExtraDataLabel">
                  <AvInput id="question-hasExtraData" type="checkbox" className="form-check-input" name="hasExtraData" />
                  <Translate contentKey="carePlannerApp.question.hasExtraData">Has Extra Data</Translate>
                </Label>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/question" replace color="info">
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
  questionEntity: storeState.question.entity,
  loading: storeState.question.loading,
  updating: storeState.question.updating,
  updateSuccess: storeState.question.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(QuestionUpdate);
