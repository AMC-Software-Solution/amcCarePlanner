import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './disability-type.reducer';
import { IDisabilityType } from 'app/shared/model/disability-type.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IDisabilityTypeDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const DisabilityTypeDetail = (props: IDisabilityTypeDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { disabilityTypeEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="carePlannerApp.disabilityType.detail.title">DisabilityType</Translate> [<b>{disabilityTypeEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="disability">
              <Translate contentKey="carePlannerApp.disabilityType.disability">Disability</Translate>
            </span>
          </dt>
          <dd>{disabilityTypeEntity.disability}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="carePlannerApp.disabilityType.description">Description</Translate>
            </span>
          </dt>
          <dd>{disabilityTypeEntity.description}</dd>
        </dl>
        <Button tag={Link} to="/disability-type" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/disability-type/${disabilityTypeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ disabilityType }: IRootState) => ({
  disabilityTypeEntity: disabilityType.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(DisabilityTypeDetail);
