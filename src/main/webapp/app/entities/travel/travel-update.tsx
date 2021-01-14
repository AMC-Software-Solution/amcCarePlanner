import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ITask } from 'app/shared/model/task.model';
import { getEntities as getTasks } from 'app/entities/task/task.reducer';
import { getEntity, updateEntity, createEntity, reset } from './travel.reducer';
import { ITravel } from 'app/shared/model/travel.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ITravelUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const TravelUpdate = (props: ITravelUpdateProps) => {
  const [taskId, setTaskId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { travelEntity, tasks, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/travel' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getTasks();
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
        ...travelEntity,
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
          <h2 id="carePlannerApp.travel.home.createOrEditLabel">
            <Translate contentKey="carePlannerApp.travel.home.createOrEditLabel">Create or edit a Travel</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : travelEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="travel-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="travel-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="travelModeLabel" for="travel-travelMode">
                  <Translate contentKey="carePlannerApp.travel.travelMode">Travel Mode</Translate>
                </Label>
                <AvInput
                  id="travel-travelMode"
                  type="select"
                  className="form-control"
                  name="travelMode"
                  value={(!isNew && travelEntity.travelMode) || 'CAR'}
                >
                  <option value="CAR">{translate('carePlannerApp.TravelMode.CAR')}</option>
                  <option value="BUS">{translate('carePlannerApp.TravelMode.BUS')}</option>
                  <option value="TRAIN">{translate('carePlannerApp.TravelMode.TRAIN')}</option>
                  <option value="PLANE">{translate('carePlannerApp.TravelMode.PLANE')}</option>
                  <option value="SHIP">{translate('carePlannerApp.TravelMode.SHIP')}</option>
                  <option value="WALK">{translate('carePlannerApp.TravelMode.WALK')}</option>
                  <option value="OTHER">{translate('carePlannerApp.TravelMode.OTHER')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="distanceToDestinationLabel" for="travel-distanceToDestination">
                  <Translate contentKey="carePlannerApp.travel.distanceToDestination">Distance To Destination</Translate>
                </Label>
                <AvField id="travel-distanceToDestination" type="string" className="form-control" name="distanceToDestination" />
              </AvGroup>
              <AvGroup>
                <Label id="timeToDestinationLabel" for="travel-timeToDestination">
                  <Translate contentKey="carePlannerApp.travel.timeToDestination">Time To Destination</Translate>
                </Label>
                <AvField id="travel-timeToDestination" type="string" className="form-control" name="timeToDestination" />
              </AvGroup>
              <AvGroup>
                <Label id="actualDistanceRequiredLabel" for="travel-actualDistanceRequired">
                  <Translate contentKey="carePlannerApp.travel.actualDistanceRequired">Actual Distance Required</Translate>
                </Label>
                <AvField id="travel-actualDistanceRequired" type="string" className="form-control" name="actualDistanceRequired" />
              </AvGroup>
              <AvGroup>
                <Label id="actualTimeRequiredLabel" for="travel-actualTimeRequired">
                  <Translate contentKey="carePlannerApp.travel.actualTimeRequired">Actual Time Required</Translate>
                </Label>
                <AvField id="travel-actualTimeRequired" type="string" className="form-control" name="actualTimeRequired" />
              </AvGroup>
              <AvGroup>
                <Label id="travelStatusLabel" for="travel-travelStatus">
                  <Translate contentKey="carePlannerApp.travel.travelStatus">Travel Status</Translate>
                </Label>
                <AvInput
                  id="travel-travelStatus"
                  type="select"
                  className="form-control"
                  name="travelStatus"
                  value={(!isNew && travelEntity.travelStatus) || 'BOOKED'}
                >
                  <option value="BOOKED">{translate('carePlannerApp.TravelStatus.BOOKED')}</option>
                  <option value="ENROUTE">{translate('carePlannerApp.TravelStatus.ENROUTE')}</option>
                  <option value="ARRIVED">{translate('carePlannerApp.TravelStatus.ARRIVED')}</option>
                  <option value="CANCELLED">{translate('carePlannerApp.TravelStatus.CANCELLED')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="createdDateLabel" for="travel-createdDate">
                  <Translate contentKey="carePlannerApp.travel.createdDate">Created Date</Translate>
                </Label>
                <AvInput
                  id="travel-createdDate"
                  type="datetime-local"
                  className="form-control"
                  name="createdDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.travelEntity.createdDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="lastUpdatedDateLabel" for="travel-lastUpdatedDate">
                  <Translate contentKey="carePlannerApp.travel.lastUpdatedDate">Last Updated Date</Translate>
                </Label>
                <AvInput
                  id="travel-lastUpdatedDate"
                  type="datetime-local"
                  className="form-control"
                  name="lastUpdatedDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.travelEntity.lastUpdatedDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="clientIdLabel" for="travel-clientId">
                  <Translate contentKey="carePlannerApp.travel.clientId">Client Id</Translate>
                </Label>
                <AvField
                  id="travel-clientId"
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
                  <AvInput id="travel-hasExtraData" type="checkbox" className="form-check-input" name="hasExtraData" />
                  <Translate contentKey="carePlannerApp.travel.hasExtraData">Has Extra Data</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label for="travel-task">
                  <Translate contentKey="carePlannerApp.travel.task">Task</Translate>
                </Label>
                <AvInput id="travel-task" type="select" className="form-control" name="taskId">
                  <option value="" key="0" />
                  {tasks
                    ? tasks.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.taskName}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/travel" replace color="info">
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
  tasks: storeState.task.entities,
  travelEntity: storeState.travel.entity,
  loading: storeState.travel.loading,
  updating: storeState.travel.updating,
  updateSuccess: storeState.travel.updateSuccess,
});

const mapDispatchToProps = {
  getTasks,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(TravelUpdate);
