# spring-webapp-restart

Example for restarting DI container on spring web application(war)

# Motivation

アプリケーション自体の再起動はすぜにコンポーネントの再作成したい。ただし特定のコンポーネント（例：外部システムとの通信コンポーネント）は接続を維持したいので再作成したくない。 ※大前提として・・・Spring Cloudの `@RefreshScope` は使わないものとする（＝Spring Clouldは利用しない）

現状のDIコンテナの構成、および問題点は以下のとおり。

![Motivation](./images/motivation.png)

# Solution

再作成が必要なものとそうでないものを別のコンテナ(アプリケーションコンテキスト)で管理さればいけそう。

![Solution](./images/solution-1.png)

コンポーネントの再作成は管理用のWeb APIを用意して、必要なタイミングで外部からキックしてもらう。

![Solution](./images/solution-2.png)

# How to manage resource as external resource

再作成対象するコンポーネントを生成するためのbean定義ファイルやプロパティファイルは、Warファイルの中ではなくAPサーバ内の外部ディレクトリの中に配置する。

![Solution](./images/how-to-manage-external-resource.png)
