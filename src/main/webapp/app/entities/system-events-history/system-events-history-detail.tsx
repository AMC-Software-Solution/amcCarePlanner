import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './system-events-history.reducer';
import { ISystemEventsHistory } from 'app/shared/model/system-events-history.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ISystemEventsHistoryDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const SystemEventsHistoryDetail = (props: ISystemEventsHistoryDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { systemEventsHistoryEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="carePlannerApp.systemEventsHistory.detail.title">SystemEventsHistory</Translate> [
          <b>{systemEventsHistoryEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="eventName">
              <Translate contentKey="carePlannerApp.systemEventsHistory.eventName">Event Name</Translate>
            </span>
          </dt>
          <dd>{systemEventsHistoryEntity.eventName}</dd>
          <dt>
            <span id="eventDate">
              <Translate contentKey="carePlannerApp.systemEventsHistory.eventDate">Event Date</Translate>
            </span>
          </dt>
          <dd>
            {systemEventsHistoryEntity.eventDate ? (
              <TextFormat value={systemEventsHistoryEntity.eventDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="eventApi">
              <Translate contentKey="carePlannerApp.systemEventsHistory.eventApi">Event Api</Translate>
            </span>
          </dt>
          <dd>{systemEventsHistoryEntity.eventApi}</dd>
          <dt>
            <span id="ipAddress">
              <Translate contentKey="carePlannerApp.systemEventsHistory.ipAddress">Ip Address</Translate>
            </span>
          </dt>
          <dd>{systemEventsHistoryEntity.ipAddress}</dd>
          <dt>
            <span id="eventNote">
              <Translate contentKey="carePlannerApp.systemEventsHistory.eventNote">Event Note</Translate>
            </span>
          </dt>
          <dd>{systemEventsHistoryEntity.eventNote}</dd>
          <dt>
            <span id="eventEntityName">
              <Translate contentKey="carePlannerApp.systemEventsHistory.eventEntityName">Event Entity Name</Translate>
            </span>
          </dt>
          <dd>{systemEventsHistoryEntity.eventEntityName}</dd>
          <dt>
            <span id="eventEntityId">
              <Translate contentKey="carePlannerApp.systemEventsHistory.eventEntityId">Event Entity Id</Translate>
            </span>
          </dt>
          <dd>{systemEventsHistoryEntity.eventEntityId}</dd>
          <dt>
            <span id="isSuspecious">
              <Translate contentKey="carePlannerApp.systemEventsHistory.isSuspecious">Is Suspecious</Translate>
            </span>
          </dt>
          <dd>{systemEventsHistoryEntity.isSuspecious ? 'true' : 'false'}</dd>
          <dt>
            <span id="callerEmail">
              <Translate contentKey="carePlannerApp.systemEventsHistory.callerEmail">Caller Email</Translate>
            </span>
          </dt>
          <dd>{systemEventsHistoryEntity.callerEmail}</dd>
          <dt>
            <span id="callerId">
              <Translate contentKey="carePlannerApp.systemEventsHistory.callerId">Caller Id</Translate>
            </span>
          </dt>
          <dd>{systemEventsHistoryEntity.callerId}</dd>
          <dt>
            <span id="clientId">
              <Translate contentKey="carePlannerApp.systemEventsHistory.clientId">Client Id</Translate>
            </span>
          </dt>
          <dd>{systemEventsHistoryEntity.clientId}</dd>
          <dt>
            <Translate contentKey="carePlannerApp.systemEventsHistory.triggedBy">Trigged By</Translate>
          </dt>
          <dd>{systemEventsHistoryEntity.triggedByEmail ? systemEventsHistoryEntity.triggedByEmail : ''}</dd>
        </dl>
        <Button tag={Link} to="/system-events-history" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/system-events-history/${systemEventsHistoryEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ systemEventsHistory }: IRootState) => ({
  systemEventsHistoryEntity: systemEventsHistory.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SystemEventsHistoryDetail);
