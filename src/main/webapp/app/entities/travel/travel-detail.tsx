import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './travel.reducer';
import { ITravel } from 'app/shared/model/travel.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITravelDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const TravelDetail = (props: ITravelDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { travelEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="carePlannerApp.travel.detail.title">Travel</Translate> [<b>{travelEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="travelMode">
              <Translate contentKey="carePlannerApp.travel.travelMode">Travel Mode</Translate>
            </span>
          </dt>
          <dd>{travelEntity.travelMode}</dd>
          <dt>
            <span id="distanceToDestination">
              <Translate contentKey="carePlannerApp.travel.distanceToDestination">Distance To Destination</Translate>
            </span>
          </dt>
          <dd>{travelEntity.distanceToDestination}</dd>
          <dt>
            <span id="timeToDestination">
              <Translate contentKey="carePlannerApp.travel.timeToDestination">Time To Destination</Translate>
            </span>
          </dt>
          <dd>{travelEntity.timeToDestination}</dd>
          <dt>
            <span id="actualDistanceRequired">
              <Translate contentKey="carePlannerApp.travel.actualDistanceRequired">Actual Distance Required</Translate>
            </span>
          </dt>
          <dd>{travelEntity.actualDistanceRequired}</dd>
          <dt>
            <span id="actualTimeRequired">
              <Translate contentKey="carePlannerApp.travel.actualTimeRequired">Actual Time Required</Translate>
            </span>
          </dt>
          <dd>{travelEntity.actualTimeRequired}</dd>
          <dt>
            <span id="travelStatus">
              <Translate contentKey="carePlannerApp.travel.travelStatus">Travel Status</Translate>
            </span>
          </dt>
          <dd>{travelEntity.travelStatus}</dd>
          <dt>
            <span id="lastUpdatedDate">
              <Translate contentKey="carePlannerApp.travel.lastUpdatedDate">Last Updated Date</Translate>
            </span>
          </dt>
          <dd>
            {travelEntity.lastUpdatedDate ? <TextFormat value={travelEntity.lastUpdatedDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="clientId">
              <Translate contentKey="carePlannerApp.travel.clientId">Client Id</Translate>
            </span>
          </dt>
          <dd>{travelEntity.clientId}</dd>
          <dt>
            <Translate contentKey="carePlannerApp.travel.task">Task</Translate>
          </dt>
          <dd>{travelEntity.taskTaskName ? travelEntity.taskTaskName : ''}</dd>
        </dl>
        <Button tag={Link} to="/travel" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/travel/${travelEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ travel }: IRootState) => ({
  travelEntity: travel.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(TravelDetail);
