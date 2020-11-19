import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './service-user-event.reducer';
import { IServiceUserEvent } from 'app/shared/model/service-user-event.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IServiceUserEventDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ServiceUserEventDetail = (props: IServiceUserEventDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { serviceUserEventEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="carePlannerApp.serviceUserEvent.detail.title">ServiceUserEvent</Translate> [
          <b>{serviceUserEventEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="eventTitle">
              <Translate contentKey="carePlannerApp.serviceUserEvent.eventTitle">Event Title</Translate>
            </span>
          </dt>
          <dd>{serviceUserEventEntity.eventTitle}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="carePlannerApp.serviceUserEvent.description">Description</Translate>
            </span>
          </dt>
          <dd>{serviceUserEventEntity.description}</dd>
          <dt>
            <span id="serviceUserEventStatus">
              <Translate contentKey="carePlannerApp.serviceUserEvent.serviceUserEventStatus">Service User Event Status</Translate>
            </span>
          </dt>
          <dd>{serviceUserEventEntity.serviceUserEventStatus}</dd>
          <dt>
            <span id="serviceUserEventType">
              <Translate contentKey="carePlannerApp.serviceUserEvent.serviceUserEventType">Service User Event Type</Translate>
            </span>
          </dt>
          <dd>{serviceUserEventEntity.serviceUserEventType}</dd>
          <dt>
            <span id="priority">
              <Translate contentKey="carePlannerApp.serviceUserEvent.priority">Priority</Translate>
            </span>
          </dt>
          <dd>{serviceUserEventEntity.priority}</dd>
          <dt>
            <span id="note">
              <Translate contentKey="carePlannerApp.serviceUserEvent.note">Note</Translate>
            </span>
          </dt>
          <dd>{serviceUserEventEntity.note}</dd>
          <dt>
            <span id="dateOfEvent">
              <Translate contentKey="carePlannerApp.serviceUserEvent.dateOfEvent">Date Of Event</Translate>
            </span>
          </dt>
          <dd>
            {serviceUserEventEntity.dateOfEvent ? (
              <TextFormat value={serviceUserEventEntity.dateOfEvent} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="lastUpdatedDate">
              <Translate contentKey="carePlannerApp.serviceUserEvent.lastUpdatedDate">Last Updated Date</Translate>
            </span>
          </dt>
          <dd>
            {serviceUserEventEntity.lastUpdatedDate ? (
              <TextFormat value={serviceUserEventEntity.lastUpdatedDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="tenantId">
              <Translate contentKey="carePlannerApp.serviceUserEvent.tenantId">Tenant Id</Translate>
            </span>
          </dt>
          <dd>{serviceUserEventEntity.tenantId}</dd>
          <dt>
            <Translate contentKey="carePlannerApp.serviceUserEvent.reportedBy">Reported By</Translate>
          </dt>
          <dd>{serviceUserEventEntity.reportedByEmployeeCode ? serviceUserEventEntity.reportedByEmployeeCode : ''}</dd>
          <dt>
            <Translate contentKey="carePlannerApp.serviceUserEvent.assignedTo">Assigned To</Translate>
          </dt>
          <dd>{serviceUserEventEntity.assignedToEmployeeCode ? serviceUserEventEntity.assignedToEmployeeCode : ''}</dd>
          <dt>
            <Translate contentKey="carePlannerApp.serviceUserEvent.serviceUser">Service User</Translate>
          </dt>
          <dd>{serviceUserEventEntity.serviceUserServiceUserCode ? serviceUserEventEntity.serviceUserServiceUserCode : ''}</dd>
        </dl>
        <Button tag={Link} to="/service-user-event" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/service-user-event/${serviceUserEventEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ serviceUserEvent }: IRootState) => ({
  serviceUserEventEntity: serviceUserEvent.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ServiceUserEventDetail);
