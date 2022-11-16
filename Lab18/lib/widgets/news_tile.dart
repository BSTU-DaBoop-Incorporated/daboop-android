
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:lab18/models/news.dart';

class NewsTile extends StatelessWidget {
  final News news;

  const NewsTile({super.key, required this.news});

  @override
  Widget build(BuildContext context) {
    return Card(
        child: Padding(
          padding: const EdgeInsets.all(16.0),
          child: Column(
            children: [
                   Row(
                      mainAxisAlignment: MainAxisAlignment.spaceBetween,

                      children: [
                    Text(news.title, style: const TextStyle(fontSize: 24, fontWeight:
                    FontWeight.bold)),
                    Text(news.publishedAt?.toDate().toLocal().toString() ?? " ", 
                        style: Theme.of
                      (context).textTheme
                        .titleSmall),]
              ),
              Text(news.text, textAlign: TextAlign.justify),
            ],
          ),
        )
    );
  }
}