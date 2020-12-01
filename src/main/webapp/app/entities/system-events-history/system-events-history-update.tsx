import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { getEntity, updateEntity, createEntity, reset } from './system-events-history.reducer';
import { ISystemEventsHistory } from 'app/shared/model/system-events-history.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ISystemEventsHistoryUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const SystemEventsHistoryUpdate = (props: ISystemEventsHistoryUpdateProps) => {
  const [triggedById, setTriggedById] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { systemEventsHistoryEntity, users, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/system-events-history' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getUsers();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.eventDate = convertDateTimeToServer(values.eventDate);

    if (errors.length === 0) {
      const entity = {
        ...systemEventsHistoryEntity,
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
          <h2 id="carePlannerApp.systemEventsHistory.home.createOrEditLabel">
            <Translate contentKey="carePlannerApp.systemEventsHistory.home.createOrEditLabel">
              Create or edit a SystemEventsHistory
            </Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : systemEventsHistoryEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="system-events-history-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="system-events-history-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="eventNameLabel" for="system-events-history-eventName">
                  <Translate contentKey="carePlannerApp.systemEventsHistory.eventName">Event Name</Translate>
                </Label>
                <AvField
                  id="system-events-history-eventName"
                  type="text"
                  name="eventName"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="eventDateLabel" for="system-events-history-eventDate">
                  <Translate contentKey="carePlannerApp.systemEventsHistory.eventDate">Event Date</Translate>
                </Label>
                <AvInput
                  id="system-events-history-eventDate"
                  type="datetime-local"
                  className="form-control"
                  name="eventDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.systemEventsHistoryEntity.eventDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="eventApiLabel" for="system-events-history-eventApi">
                  <Translate contentKey="carePlannerApp.systemEventsHistory.eventApi">Event Api</Translate>
                </Label>
                <AvField id="system-events-history-eventApi" type="text" name="eventApi" />
              </AvGroup>
              <AvGroup>
                <Label id="ipAddressLabel" for="system-events-history-ipAddress">
                  <Translate contentKey="carePlannerApp.systemEventsHistory.ipAddress">Ip Address</Translate>
                </Label>
                <AvField id="system-events-history-ipAddress" type="text" name="ipAddress" />
              </AvGroup>
              <AvGroup>
                <Label id="eventNoteLabel" for="system-events-history-eventNote">
                  <Translate contentKey="carePlannerApp.systemEventsHistory.eventNote">Event Note</Translate>
                </Label>
                <AvField id="system-events-history-eventNote" type="text" name="eventNote" />
              </AvGroup>
              <AvGroup>
                <Label id="eventEntityNameLabel" for="system-events-history-eventEntityName">
                  <Translate contentKey="carePlannerApp.systemEventsHistory.eventEntityName">Event Entity Name</Translate>
                </Label>
                <AvField id="system-events-history-eventEntityName" type="text" name="eventEntityName" />
              </AvGroup>
              <AvGroup>
                <Label id="eventEntityIdLabel" for="system-events-history-eventEntityId">
                  <Translate contentKey="carePlannerApp.systemEventsHistory.eventEntityId">Event Entity Id</Translate>
                </Label>
                <AvField id="system-events-history-eventEntityId" type="string" className="form-control" name="eventEntityId" />
              </AvGroup>
              <AvGroup check>
                <Label id="isSuspeciousLabel">
                  <AvInput id="system-events-history-isSuspecious" type="checkbox" className="form-check-input" name="isSuspecious" />
                  <Translate contentKey="carePlannerApp.systemEventsHistory.isSuspecious">Is Suspecious</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label id="clientIdLabel" for="system-events-history-clientId">
                  <Translate contentKey="carePlannerApp.systemEventsHistory.clientId">Client Id</Translate>
                </Label>
                <AvField id="system-events-history-clientId" type="string" className="form-control" name="clientId" />
              </AvGroup>
              <AvGroup>
                <Label for="system-events-history-triggedBy">
                  <Translate contentKey="carePlannerApp.systemEventsHistory.triggedBy">Trigged By</Translate>
                </Label>
                <AvInput id="system-events-history-triggedBy" type="select" className="form-control" name="triggedById">
                  <option value="" key="0" />
                  {users
                    ? users.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.login}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/system-events-history" replace color="info">
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
  users: storeState.userManagement.users,
  systemEventsHistoryEntity: storeState.systemEventsHistory.entity,
  loading: storeState.systemEventsHistory.loading,
  updating: storeState.systemEventsHistory.updating,
  updateSuccess: storeState.systemEventsHistory.updateSuccess,
});

const mapDispatchToProps = {
  getUsers,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SystemEventsHistoryUpdate);
