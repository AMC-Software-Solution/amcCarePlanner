import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './currency.reducer';
import { ICurrency } from 'app/shared/model/currency.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICurrencyDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CurrencyDetail = (props: ICurrencyDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { currencyEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="carePlannerApp.currency.detail.title">Currency</Translate> [<b>{currencyEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="currency">
              <Translate contentKey="carePlannerApp.currency.currency">Currency</Translate>
            </span>
          </dt>
          <dd>{currencyEntity.currency}</dd>
          <dt>
            <span id="currencyIsoCode">
              <Translate contentKey="carePlannerApp.currency.currencyIsoCode">Currency Iso Code</Translate>
            </span>
          </dt>
          <dd>{currencyEntity.currencyIsoCode}</dd>
          <dt>
            <span id="currencySymbol">
              <Translate contentKey="carePlannerApp.currency.currencySymbol">Currency Symbol</Translate>
            </span>
          </dt>
          <dd>{currencyEntity.currencySymbol}</dd>
          <dt>
            <span id="currencyLogo">
              <Translate contentKey="carePlannerApp.currency.currencyLogo">Currency Logo</Translate>
            </span>
          </dt>
          <dd>
            {currencyEntity.currencyLogo ? (
              <div>
                {currencyEntity.currencyLogoContentType ? (
                  <a onClick={openFile(currencyEntity.currencyLogoContentType, currencyEntity.currencyLogo)}>
                    <img
                      src={`data:${currencyEntity.currencyLogoContentType};base64,${currencyEntity.currencyLogo}`}
                      style={{ maxHeight: '30px' }}
                    />
                  </a>
                ) : null}
                <span>
                  {currencyEntity.currencyLogoContentType}, {byteSize(currencyEntity.currencyLogo)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="currencyLogoUrl">
              <Translate contentKey="carePlannerApp.currency.currencyLogoUrl">Currency Logo Url</Translate>
            </span>
          </dt>
          <dd>{currencyEntity.currencyLogoUrl}</dd>
          <dt>
            <span id="hasExtraData">
              <Translate contentKey="carePlannerApp.currency.hasExtraData">Has Extra Data</Translate>
            </span>
          </dt>
          <dd>{currencyEntity.hasExtraData ? 'true' : 'false'}</dd>
        </dl>
        <Button tag={Link} to="/currency" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/currency/${currencyEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ currency }: IRootState) => ({
  currencyEntity: currency.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CurrencyDetail);
