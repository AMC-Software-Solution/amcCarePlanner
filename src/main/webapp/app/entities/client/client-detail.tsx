import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './client.reducer';
import { IClient } from 'app/shared/model/client.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IClientDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ClientDetail = (props: IClientDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { clientEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="carePlannerApp.client.detail.title">Client</Translate> [<b>{clientEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="clientName">
              <Translate contentKey="carePlannerApp.client.clientName">Client Name</Translate>
            </span>
          </dt>
          <dd>{clientEntity.clientName}</dd>
          <dt>
            <span id="clientDescription">
              <Translate contentKey="carePlannerApp.client.clientDescription">Client Description</Translate>
            </span>
          </dt>
          <dd>{clientEntity.clientDescription}</dd>
          <dt>
            <span id="clientLogo">
              <Translate contentKey="carePlannerApp.client.clientLogo">Client Logo</Translate>
            </span>
          </dt>
          <dd>
            {clientEntity.clientLogo ? (
              <div>
                {clientEntity.clientLogoContentType ? (
                  <a onClick={openFile(clientEntity.clientLogoContentType, clientEntity.clientLogo)}>
                    <img
                      src={`data:${clientEntity.clientLogoContentType};base64,${clientEntity.clientLogo}`}
                      style={{ maxHeight: '30px' }}
                    />
                  </a>
                ) : null}
                <span>
                  {clientEntity.clientLogoContentType}, {byteSize(clientEntity.clientLogo)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="clientLogoUrl">
              <Translate contentKey="carePlannerApp.client.clientLogoUrl">Client Logo Url</Translate>
            </span>
          </dt>
          <dd>{clientEntity.clientLogoUrl}</dd>
          <dt>
            <span id="clientContactName">
              <Translate contentKey="carePlannerApp.client.clientContactName">Client Contact Name</Translate>
            </span>
          </dt>
          <dd>{clientEntity.clientContactName}</dd>
          <dt>
            <span id="clientPhone">
              <Translate contentKey="carePlannerApp.client.clientPhone">Client Phone</Translate>
            </span>
          </dt>
          <dd>{clientEntity.clientPhone}</dd>
          <dt>
            <span id="clientEmail">
              <Translate contentKey="carePlannerApp.client.clientEmail">Client Email</Translate>
            </span>
          </dt>
          <dd>{clientEntity.clientEmail}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="carePlannerApp.client.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>{clientEntity.createdDate ? <TextFormat value={clientEntity.createdDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="lastUpdatedDate">
              <Translate contentKey="carePlannerApp.client.lastUpdatedDate">Last Updated Date</Translate>
            </span>
          </dt>
          <dd>
            {clientEntity.lastUpdatedDate ? <TextFormat value={clientEntity.lastUpdatedDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
        </dl>
        <Button tag={Link} to="/client" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/client/${clientEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ client }: IRootState) => ({
  clientEntity: client.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ClientDetail);
