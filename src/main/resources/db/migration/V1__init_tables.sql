-- db/migration/V1__init_tables.sql
CREATE TABLE "rental_rates" (
    "id_rate" SERIAL PRIMARY KEY,
    "price_per_hour" NUMERIC(10, 2) NOT NULL,    -- Цена за час
    "price_per_minute" NUMERIC(10, 2) NOT NULL,  -- Цена за минуту
    "price_per_day" NUMERIC(10, 2) NOT NULL      -- Цена за день
);

CREATE TABLE "location" (
    "id_location" SERIAL PRIMARY KEY,
    "name" VARCHAR(255) NOT NULL,               -- Название места
    "address" VARCHAR(255) NOT NULL,            -- Адрес
    "latitude" NUMERIC(10, 7) NOT NULL,         -- Широта
    "longitude" NUMERIC(10, 7) NOT NULL,        -- Долгота
    "created_at" TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP -- Дата создания
);


CREATE TABLE "personal_info" (
     "id_info" SERIAL PRIMARY KEY,
     "first_name" VARCHAR(255) NOT NULL,               -- Имя пользователя
     "last_name" VARCHAR(255) NOT NULL,                -- Фамилия пользователя
     "passport_number" INT NOT NULL,                   -- Номер паспорта
     "passport_issue_date" DATE NOT NULL,              -- Дата выдачи паспорта
     "passport_issued_by" VARCHAR(255) NOT NULL,       -- Орган, выдавший паспорт
     "dl_number" VARCHAR(50) NOT NULL,                 -- Номер водительского удостоверения
     "dl_issue_date" DATE NOT NULL,                    -- Дата выдачи водительского удостоверения
     "dl_expiry_date" DATE NOT NULL,                   -- Дата истечения срока действия водительского удостоверения
     "updated_at" TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, -- Дата последнего обновления
     "id_user" INT NOT NULL,                           -- Внешний ключ к таблице user
     CONSTRAINT fk_user_personal_info FOREIGN KEY ("id_user") REFERENCES "user"("id_user") ON DELETE CASCADE
);


CREATE TABLE "car" (
   "id_car" SERIAL PRIMARY KEY,                           -- Уникальный идентификатор автомобиля
   "brand" VARCHAR(255) NOT NULL,                         -- Бренд автомобиля
   "model" VARCHAR(255) NOT NULL,                         -- Модель автомобиля
   "licence_plate" VARCHAR(50) NOT NULL UNIQUE,           -- Номерной знак
   "created_at" TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, -- Дата создания записи
   "image_path" VARCHAR(255) NOT NULL,                    -- Путь к изображению автомобиля
   "fuel_level" INT NOT NULL,                             -- Уровень топлива в процентах
   "fuel_tank_capacity" INT NOT NULL,                     -- Вместимость топливного бака
   "transmission" VARCHAR(30) NOT NULL,             -- Тип трансмиссии (MANUAL или AUTOMATIC)
   "drive_type" VARCHAR(4) NOT NULL,                      -- Тип привода (FWD, RWD или AWD)
   "engine_volume" NUMERIC(5, 2) NOT NULL,                -- Объем двигателя в литрах
   "engine_power" INT NOT NULL,                           -- Мощность двигателя (л.с.)
   "heated_seats" BOOLEAN NOT NULL DEFAULT FALSE,         -- Наличие подогрева сидений
   "heated_steering_wheel" BOOLEAN NOT NULL DEFAULT FALSE,-- Наличие подогрева руля
   "parking_sensors" BOOLEAN NOT NULL DEFAULT FALSE,      -- Наличие парковочных датчиков
   "touch_screen" BOOLEAN NOT NULL DEFAULT FALSE,         -- Наличие сенсорного экрана
   "id_location" INT NOT NULL,                            -- Внешний ключ к таблице location
   "id_rental_rates" INT NOT NULL,                        -- Внешний ключ к таблице rental_rates
   "status" VARCHAR(12) NOT NULL DEFAULT 'AVAILABLE',      -- Статус автомобиля (по умолчанию AVAILABLE)
   CONSTRAINT fk_location FOREIGN KEY ("id_location") REFERENCES "location"("id_location") ON DELETE SET NULL,
   CONSTRAINT fk_rental_rates FOREIGN KEY ("id_rental_rates") REFERENCES "rental_rates"("id_rate") ON DELETE SET NULL
);


CREATE TABLE "rental" (
  "id_rental" SERIAL PRIMARY KEY,
  "rental_type" VARCHAR(50) NOT NULL,             -- Тип аренды (например, MINUTE, HOUR, DAY)
  "price_per_smth" NUMERIC(10, 2) NOT NULL,       -- Цена за единицу (минута, час или день)
  "start_time" TIMESTAMP NOT NULL,                -- Время начала аренды
  "end_time" TIMESTAMP,                           -- Время окончания аренды (nullable)
  "total_price" NUMERIC(10, 2),                   -- Итоговая цена (nullable)
  "distance" INT,                                 -- Пройденное расстояние (nullable)
  "duration" INT,                                 -- Продолжительность аренды в минутах (nullable)
  "id_car" INT NOT NULL,                          -- Внешний ключ к таблице car
  "id_user" INT NOT NULL,                         -- Внешний ключ к таблице user
  "status" VARCHAR(50) NOT NULL DEFAULT 'ACTIVE', -- Статус аренды (по умолчанию ACTIVE)
  CONSTRAINT fk_car FOREIGN KEY ("id_car") REFERENCES "car"("id_car") ON DELETE NO ACTION,
  CONSTRAINT fk_user FOREIGN KEY ("id_user") REFERENCES "user"("id_user") ON DELETE NO ACTION
);