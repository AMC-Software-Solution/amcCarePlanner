import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IConsent, defaultValue } from 'app/shared/model/consent.model';

export const ACTION_TYPES = {
  FETCH_CONSENT_LIST: 'consent/FETCH_CONSENT_LIST',
  FETCH_CONSENT: 'consent/FETCH_CONSENT',
  CREATE_CONSENT: 'consent/CREATE_CONSENT',
  UPDATE_CONSENT: 'consent/UPDATE_CONSENT',
  DELETE_CONSENT: 'consent/DELETE_CONSENT',
  SET_BLOB: 'consent/SET_BLOB',
  RESET: 'consent/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IConsent>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type ConsentState = Readonly<typeof initialState>;

// Reducer

export default (state: ConsentState = initialState, action): ConsentState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_CONSENT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_CONSENT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_CONSENT):
    case REQUEST(ACTION_TYPES.UPDATE_CONSENT):
    case REQUEST(ACTION_TYPES.DELETE_CONSENT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_CONSENT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_CONSENT):
    case FAILURE(ACTION_TYPES.CREATE_CONSENT):
    case FAILURE(ACTION_TYPES.UPDATE_CONSENT):
    case FAILURE(ACTION_TYPES.DELETE_CONSENT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_CONSENT_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_CONSENT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_CONSENT):
    case SUCCESS(ACTION_TYPES.UPDATE_CONSENT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_CONSENT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.SET_BLOB: {
      const { name, data, contentType } = action.payload;
      return {
        ...state,
        entity: {
          ...state.entity,
          [name]: data,
          [name + 'ContentType']: contentType,
        },
      };
    }
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/consents';

// Actions

export const getEntities: ICrudGetAllAction<IConsent> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_CONSENT_LIST,
    payload: axios.get<IConsent>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IConsent> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_CONSENT,
    payload: axios.get<IConsent>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IConsent> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_CONSENT,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IConsent> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_CONSENT,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IConsent> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_CONSENT,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const setBlob = (name, data, contentType?) => ({
  type: ACTION_TYPES.SET_BLOB,
  payload: {
    name,
    data,
    contentType,
  },
});

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
