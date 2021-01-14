import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './relationship-type.reducer';
import { IRelationshipType } from 'app/shared/model/relationship-type.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IRelationshipTypeDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const RelationshipTypeDetail = (props: IRelationshipTypeDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { relationshipTypeEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="carePlannerApp.relationshipType.detail.title">RelationshipType</Translate> [
          <b>{relationshipTypeEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="relationType">
              <Translate contentKey="carePlannerApp.relationshipType.relationType">Relation Type</Translate>
            </span>
          </dt>
          <dd>{relationshipTypeEntity.relationType}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="carePlannerApp.relationshipType.description">Description</Translate>
            </span>
          </dt>
          <dd>{relationshipTypeEntity.description}</dd>
          <dt>
            <span id="clientId">
              <Translate contentKey="carePlannerApp.relationshipType.clientId">Client Id</Translate>
            </span>
          </dt>
          <dd>{relationshipTypeEntity.clientId}</dd>
          <dt>
            <span id="hasExtraData">
              <Translate contentKey="carePlannerApp.relationshipType.hasExtraData">Has Extra Data</Translate>
            </span>
          </dt>
          <dd>{relationshipTypeEntity.hasExtraData ? 'true' : 'false'}</dd>
        </dl>
        <Button tag={Link} to="/relationship-type" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/relationship-type/${relationshipTypeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ relationshipType }: IRootState) => ({
  relationshipTypeEntity: relationshipType.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(RelationshipTypeDetail);
