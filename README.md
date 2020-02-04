## Environment

Install Google SDK: https://cloud.google.com/sdk/install

```
echo "deb [signed-by=/usr/share/keyrings/cloud.google.gpg] http://packages.cloud.google.com/apt cloud-sdk main" | sudo tee -a /etc/apt/sources.list.d/google-cloud-sdk.list
curl https://packages.cloud.google.com/apt/doc/apt-key.gpg | sudo apt-key --keyring /usr/share/keyrings/cloud.google.gpg add -
sudo apt-get update && sudo apt-get install google-cloud-sdk
gcloud init
gcloud auth list
```

Export credentials: https://cloud.google.com/docs/authentication/production#obtaining_and_providing_service_account_credentials_manually

```
gcloud iam service-accounts create upflinkpubsub
gcloud projects add-iam-policy-binding upflinkpubsub --member "serviceAccount:upflinkpubsub@upflinkpubsub.iam.gserviceaccount.com" --role "roles/owner"
gcloud iam service-accounts keys create /home/antonmry/Workspace/GaliGlobal/Flink/upflinkpubsub.json --iam-account upflinkpubsub@upflinkpubsub.iam.gserviceaccount.com
export GOOGLE_APPLICATION_CREDENTIALS="/home/antonmry/Workspace/GaliGlobal/Flink/upflinkpubsub.json"
```

Create pubsub topic: https://cloud.google.com/pubsub/docs/quickstart-cli

```
gcloud pubsub topics create my-topic
gcloud pubsub subscriptions create --topic my-topic my-sub
gcloud pubsub subscriptions pull --auto-ack my-sub
export GCP_PROJECT=upflinkpubsub
export PUBSUB_TOPIC=my-topic
export PUBSUB_SUBSCRIPTION=my-sub
```

Install flink (optional):

```
http://apache.40b.nl/flink/flink-1.9.2/flink-1.9.2-bin-scala_2.12.tgz
tar -zxvf  flink-1.9.2-bin-scala_2.12.tgz
flink-1.9.2/bin/start-cluster.sh
xdg-open http://localhost:8081
```

## Test

Generate events in PubSub:

```
./gradlew test --tests "GooglePubSubGeneratorTests.generatePubSubMessages"
```

Compile and deploy (option with Flink cluster):

```
./gradlew build -x test
../flink-1.9.2/bin/flink run build/libs/stream-processing-demo-0.1.0.jar
tail -f ../flink-1.9.2/log/flink-*-standalonesession-*.log
```

Compile and deploy (option with embedded Flink cluster):

```
./gradlew run
```
