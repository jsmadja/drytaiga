#!/bin/bash
git push github && git push heroku && heroku logs -t --app drytaiga
